package model.effects;

import model.world.Champion;

public class Shield extends Effect {

	public Shield( int duration) {
		super("Shield", duration, EffectType.BUFF);
		
	}
	public String toString() {
		String info="Block the next attack or damaging  \n ability cast on target "
				+ "Once an attack or \n ability is blocked,\n the effect should be removed \n"
				+ "Increase speed by 2%";
		return info;
	}

	@Override
	public void apply(Champion c) {
		
		c.setSpeed((int) (c.getSpeed()*1.02));
	}

	@Override
	public void remove(Champion c) {
		
		c.setSpeed((int) (c.getSpeed()/1.02));
	}

}
