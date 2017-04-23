import pandas as pd
from bokeh.plotting import figure, save, output_file
from bokeh.sampledata.stocks import MSFT
from math import pi
from multiprocessing import Pool



def getSymbols():
    with open("../data/symbols.csv","r") as f:
        symbols = f.readlines()
    f.close()
    return symbols

def process(symbol):
	filename = "../data/histData/"+symbol+".csv"
	try:
		
		df = pd.read_csv(filename)
		#print(symbol+"  read done")
	
		df.columns = ["date","open","high","low","close","volume","adj_close"]
		df["date"] = pd.to_datetime(df["date"])
		inc = df.close > df.open
		dec = df.open > df.close
		w = 12*60*60*1000 # half day in ms

		TOOLS = "pan,wheel_zoom,box_zoom,reset,save"

		p = figure(x_axis_type="datetime", tools=TOOLS, plot_width=1000, title = "MSFT Candlestick")
		p.xaxis.major_label_orientation = pi/4
		p.grid.grid_line_alpha=0.3

		p.segment(df.date, df.high, df.date, df.low, color="black")
		p.vbar(df.date[inc], w, df.open[inc], df.close[inc], fill_color="#D5E1DD", line_color="black")
		p.vbar(df.date[dec], w, df.open[dec], df.close[dec], fill_color="#F2583E", line_color="black")
		html = symbol+"_candlestick.html"
		title = symbol+" prices"
		output_file(html, title=title)
		save(p)
		print(symbol+"done!")
	except:
		return



symbols_ = getSymbols()
symbols = []
for symbol in symbols_:
	symbols.append(symbol.rstrip('\n'))
	
#symbols = ["GOOG"]
#for symbol in symbols:
#	process(symbol)
p = Pool(5)
p.map(process,symbols)





