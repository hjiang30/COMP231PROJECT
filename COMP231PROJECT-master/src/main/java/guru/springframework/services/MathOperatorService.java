package guru.springframework.services;

import java.util.List;

import org.apache.commons.lang3.tuple.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import guru.springframework.domain.EMAState;
import guru.springframework.domain.HistoricalData;
import rx.Observable;
import rx.functions.*;
import rx.math.operators.OperatorMinMax;
import rx.observables.MathObservable;

public class MathOperatorService {

	public static Observable<Pair<DateTime, Double>> getClose(String symbol, DateTime begin) {
		return null;

	}

	public static Observable<Double> movingAverage(Observable<Double> o, int N) {
		return o.window(N, 1).flatMap(new Func1<Observable<Double>, Observable<Double>>() {
			public Observable<Double> call(Observable<Double> window) {
				return MathObservable.averageDouble(window);
			}
		});
	}

	public static Observable<Double> avg(Observable<Double> o, int N) {
		return o.window(N, 1).flatMap((win) -> {
			return win.reduce(0.0, (s, e) -> (s + e))

					.map(s -> s / N);

		}).skipLast(N - 1);
	}

	public static Observable<Pair<DateTime, Double>> STD(Observable<Pair<DateTime, Double>> data, int N) {
		Observable<DateTime> timeStamp = data.map(x -> x.getLeft()).skip(N - 1);
		Observable<Double> data_v = data.map(x -> x.getRight());
		Observable<Double> avg_v = MathOperatorService.avg(data_v, N);
		Observable<Double> o = Observable.zip(data_v.window(N, 1).skipLast(N - 1),

				avg_v, (Observable<Double> a, Double b) -> {
					return a.map(item -> (item - b) * (item - b)).reduce((s, c) -> (s + c))
							.map(x -> Math.sqrt(x / (N - 1))).first().toBlocking().last();
				});
		return Observable.zip(timeStamp, o, (a1, a2) -> new ImmutablePair(a1, a2));
	}

	public static Observable<Pair<DateTime, Double>> MeanDeviation(Observable<Pair<DateTime, Double>> data, int N) {
		Observable<DateTime> timeStamp = data.map(x -> x.getLeft()).skip(N - 1);
		Observable<Double> data_v = data.map(x -> x.getRight());
		Observable<Double> avg_v = MathOperatorService.avg(data_v, N);
		Observable<Double> o = Observable.zip(data_v.window(N, 1).skipLast(N - 1),

				avg_v, (Observable<Double> a, Double b) -> {
					return a.map(item -> Math.abs(item - b)).reduce((s, c) -> (s + c)).map(x -> x / N).first()
							.toBlocking().last();
				});
		return Observable.zip(timeStamp, o, (a1, a2) -> new ImmutablePair(a1, a2));
	}

	public static Observable<Double> highOfHigh(Observable<Double> o, int N) {
		return o.window(N, 1).flatMap(new Func1<Observable<Double>, Observable<Double>>() {
			public Observable<Double> call(Observable<Double> window) {
				return OperatorMinMax.max(window);
			}
		});
	}

	public static Observable<Double> lowOfLow(Observable<Double> o, int N) {
		return o.window(N, 1).flatMap(new Func1<Observable<Double>, Observable<Double>>() {
			public Observable<Double> call(Observable<Double> window) {
				return OperatorMinMax.min(window);
			}
		});
	}
	
	
	// use adjclose instead of close.
		public static Observable<Pair<DateTime, Double>> CLOSE(Observable<Pair<DateTime, HistoricalData>> data) {
			return Observable.zip(data.map(p -> p.getLeft()),
					data.map(p -> Double.parseDouble(p.getRight().getAdjClosePrice())), (t, p) -> new ImmutablePair(t, p));
		}

		public static Observable<Pair<DateTime, Double>> HIGH(Observable<Pair<DateTime, HistoricalData>> data) {
			return Observable.zip(data.map(p -> p.getLeft()),
					data.map(p -> Double.parseDouble(p.getRight().getHighPrice())), (t, p) -> new ImmutablePair(t, p));
		}

		public static Observable<Pair<DateTime, Double>> LOW(Observable<Pair<DateTime, HistoricalData>> data) {
			return Observable.zip(data.map(p -> p.getLeft()), data.map(p -> Double.parseDouble(p.getRight().getLowPrice())),
					(t, p) -> new ImmutablePair(t, p));
		}

		public static Observable<Pair<DateTime, Double>> VOLUME(Observable<Pair<DateTime, HistoricalData>> data) {
			return Observable.zip(data.map(p -> p.getLeft()), data.map(p -> Double.parseDouble(p.getRight().getVolume())),
					(t, p) -> new ImmutablePair(t, p));
		}

		// SMA function
		public static Observable<Pair<DateTime, Double>> SMA(Observable<Pair<DateTime, Double>> o, int N) {
			return Observable.zip(o.map(t -> t.getLeft()).skip(N - 1),
					MathOperatorService.movingAverage(o.map(t -> t.getRight()), N).skipLast(N - 1),
					(t, p) -> new ImmutablePair(t, p));
		}

		public static Observable<Pair<DateTime, Double>> HOH(Observable<Pair<DateTime, Double>> o, int N) {
			return Observable.zip(o.map(t -> t.getLeft()).skip(N - 1),
					MathOperatorService.highOfHigh(o.map(t -> t.getRight()), N).skipLast(N - 1),
					(t, p) -> new ImmutablePair(t, p));
		}

		public static Observable<Pair<DateTime, Double>> LOL(Observable<Pair<DateTime, Double>> o, int N) {
			return Observable.zip(o.map(t -> t.getLeft()).skip(N - 1),
					MathOperatorService.lowOfLow(o.map(t -> t.getRight()), N).skipLast(N - 1),
					(t, p) -> new ImmutablePair(t, p));
		}

		public static Observable<Pair<DateTime, Double>> WILLIAM(Observable<Pair<DateTime, Double>> close,
				Observable<Pair<DateTime, Double>> hoh, Observable<Pair<DateTime, Double>> lol, int N) {

			Observable<DateTime> time = close.map(x -> x.getLeft()).skip(N - 1);
			Observable<Double> close_value = close.map(x -> x.getRight()).skip(N - 1);
			Observable<Double> hoh_value = hoh.map(x -> x.getRight());
			Observable<Double> lol_value = lol.map(x -> x.getRight());
			Observable<Double> result = Observable.zip(close_value, hoh_value, lol_value,
					(c, hn, ln) -> MathOperatorService.william_func(c, hn, ln));
			return Observable.zip(time, result, (t, p) -> new ImmutablePair(t, p * (-1)));

		}

		public static double william_func(double c, double hn, double ln) {
			return 100 - (c - ln) / (hn - ln) * 100;
		}

		public static EMAState updateEMAState(EMAState ema, Pair<DateTime, Double> e, double multiplier) {
			EMAState newEMA = new EMAState(e.getLeft(), 0.0, 0);

			if (ema.n == 0) {
				newEMA.n += 1;
				return newEMA;
			} else {
				newEMA.n = ema.n + 1;
				newEMA.value = (e.getRight() - ema.value) * multiplier + ema.value;
				return newEMA;
			}
		}

		public static Observable<Pair<DateTime, Double>> EMATEST(Observable<Pair<DateTime, Double>> close, int N) {
			double multiplier = 2.0 / (N + 1);
			Observable<Pair<DateTime, Double>> sma = MathOperatorService.SMA(close, N).first();
			double smaValue = sma.toBlocking().last().getRight();
			Observable<EMAState> result = close.skip(N - 1).scan(new EMAState(DateTime.now(), smaValue, 0),
					(s, e) -> MathOperatorService.updateEMAState(s, e, multiplier)).skip(1);

			return result.map((EMAState x) -> new ImmutablePair(x.t, x.value));
		}

		public static Observable<Pair<DateTime, Double>> EMA(Observable<Pair<DateTime, Double>> close, int N) {
			double multiplier = 2.0 / (N + 1);
			Observable<Pair<DateTime, Double>> sma = MathOperatorService.SMA(close, N).first();
			double smaValue = sma.toBlocking().last().getRight();
			Observable<EMAState> result = close.skip(N - 1).scan(new EMAState(DateTime.now(), smaValue, 0), (s, e) -> {
				s.t = e.getLeft();
				if (s.n < 1)
					s.value = smaValue;
				else {
					s.value = (e.getRight() - s.value) * multiplier + s.value;
				}

				s.n += 1;
				return s;
			}).skip(1);
			return result.map((EMAState x) -> new ImmutablePair(x.t, x.value));
		}

		// need s< N1 < N2 !!!!
		public static Observable<Pair<DateTime, Triple<Double, Double, Double>>> MACD(
				Observable<Pair<DateTime, Double>> close, int N1, int N2, int S) {

			Observable<Pair<DateTime, Double>> ema1 = MathOperatorService.EMA(close, N1).skip(N2 - N1);

			Observable<Pair<DateTime, Double>> ema2 = MathOperatorService.EMA(close, N2);

			Observable<Pair<DateTime, Double>> macd_v = Observable.zip(ema1, ema2, (x1, x2) -> {

				return new ImmutablePair(x1.getLeft(), x1.getRight() - x2.getRight());
			});

			List<Pair<DateTime, Double>> listMACD = macd_v.toList().toBlocking().single();

			Observable<Pair<DateTime, Double>> signal = MathOperatorService.EMA(Observable.from(listMACD), S);

			List<Pair<DateTime, Double>> listSignal = signal.toList().toBlocking().single();// .skip(N2-S);

			return Observable.zip(Observable.from(listMACD).skip(S - 1), Observable.from(listSignal),
					(t1, s) -> (new ImmutablePair(t1.getLeft(), new ImmutableTriple<Double, Double, Double>(t1.getRight(),
							s.getRight(), t1.getRight() - s.getRight()))));
		}
		
		
		public static Observable<Pair<DateTime, Double>> RSI(Observable<Pair<DateTime, Double>> close, int N) {
			Observable<Pair<DateTime, Double>> diff = MathOperatorService.DIFF(close);

			Observable<Pair<DateTime, Double>> uSeries = diff
					.map(x -> new ImmutablePair(x.getLeft(), upperLadderFunction(x.getRight())));

			Observable<Pair<DateTime, Double>> lSeries = diff
					.map(x -> new ImmutablePair(x.getLeft(), lowerLadderFunction(x.getRight())));

			Observable<Pair<DateTime, Double>> emaU = MathOperatorService.SMA(uSeries, N);

			Observable<Pair<DateTime, Double>> emaL = MathOperatorService.SMA(lSeries, N);

			return Observable.zip(emaU, emaL,
					(h, l) -> new ImmutablePair(h.getLeft(), 100 - 100 / (1 - h.getRight() / l.getRight())));
		};
		
		// one order diff of timeseries
		public static Observable<Pair<DateTime, Double>> DIFF(Observable<Pair<DateTime, Double>> o) {

			Observable<DateTime> t = o.map(x -> x.getLeft()).skip(1);
			Observable<Double> v = o.map(x -> x.getRight()).window(2, 1).skipLast(1)
					.flatMap(win -> win.reduce(0.0, (s, c) -> {
						return ((-1) * s + c);
					}));

			return Observable.zip(t, v, (a, b) -> new ImmutablePair(a, b));

		}
		
		public static double upperLadderFunction(double a) {
			if (a >= 0)
				return a;
			else {
				return 0.0;
			}

		}

		public static double lowerLadderFunction(double a) {
			if (a <= 0)
				return a;
			else {
				return 0.0;
			}

		}
		
		public static Observable<Triple<DateTime, Double, Double>> KDJ(Observable<Pair<DateTime, Double>> close,
				Observable<Pair<DateTime, Double>> high, Observable<Pair<DateTime, Double>> low, int N, int A) {

			Observable<Pair<DateTime, Double>> hoh = MathOperatorService.HOH(high, N);
			Observable<Pair<DateTime, Double>> lol = MathOperatorService.LOL(low, N);

			Observable<Pair<DateTime, Double>> k = Observable.zip(close.skip(N - 1), hoh, lol,
					(c, h, l) -> new ImmutablePair(c.getLeft(),
							100 * (c.getRight() - l.getRight()) / (h.getRight() - l.getRight())));

			Observable<Pair<DateTime, Double>> d = MathOperatorService.SMA(k, A);

			Observable<Triple<DateTime, Double, Double>> result = Observable.zip(k.skip(A - 1), d,
					(k_, d_) -> new ImmutableTriple(k_.getLeft(), k_.getRight(), d_.getRight()));
			return result;

		}
		
		public static Observable<Pair<DateTime, Triple<Double, Double, Double>>> BOLL(
				Observable<Pair<DateTime, Double>> close, int N, double alpha) {
			Observable<Pair<DateTime, Double>> middleLine = MathOperatorService.SMA(close, N);
			Observable<Pair<DateTime, Double>> std = MathOperatorService.STD(close, N);
			Observable<Pair<DateTime, Double>> upperBound = Observable.zip(middleLine, std,
					(m, d) -> new ImmutablePair(m.getLeft(), m.getRight() + d.getRight() * alpha));
			Observable<Pair<DateTime, Double>> lowerBound = Observable.zip(middleLine, std,
					(m, d) -> new ImmutablePair(m.getLeft(), m.getRight() - d.getRight() * alpha));

			return Observable.zip(lowerBound, middleLine, upperBound, (a, b, c) -> new ImmutablePair(a.getLeft(),
					new ImmutableTriple<Double, Double, Double>(a.getRight(), b.getRight(), c.getRight())));
		}
	
	
}
