package edu.umich.verdict.relation.condition;

public class NotCond extends Cond {
	
	private Cond cond;
	
	public NotCond(Cond cond) {
		this.cond = cond;
	}
	
	public static NotCond from(Cond cond) {
		return new NotCond(cond);
	}

	@Override
	public String toString() {
		return String.format("NOT %s", cond);
	}
}
