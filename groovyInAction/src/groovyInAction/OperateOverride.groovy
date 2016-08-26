package groovyInAction

class OperateOverride {
	private int amount
	private String currency

	OperateOverride(amountVal,currencyVal){
		amount=amountVal
		currency=currencyVal
	}

	boolean equals(OperateOverride oo){
		if(null==oo)return false
		if(!(oo instanceof OperateOverride)) return false
		if(currency!=oo.currency)return false
		if(amount!=oo.amount)return false
		return true
	}
	int hashCode(){
		amount.hashCode()+currency.hashCode()
	}
	OperateOverride plus(OperateOverride oo){
		if(null==oo)return null
		if(oo.currency!=currency){
			throw new IllegalArgumentException("cannot add $oo.currency to currency")
		}
		return new OperateOverride(oo.amount+this.amount,currency)
	}
	OperateOverride plus(Integer i){
		new OperateOverride(this.amount+i,
				currency)
	}


	static void main(args){
		OperateOverride oo=new OperateOverride(5,"USD")
		assert oo
		assert oo==new OperateOverride(5,'USD')
		assert oo+oo==new OperateOverride(10,"USD")
		assert oo+5==new OperateOverride(10,"USD")
		
		println ((5.0+10).class)
	}
}
