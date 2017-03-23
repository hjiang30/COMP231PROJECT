package guru.springframework.services;

import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;

import rx.Observable;

public interface Rule {
	
	public Observable<Pair<DateTime,Double>> runRule(RuleImplService ruleUtils);
	
	public Rule andRule(Rule rule);

}