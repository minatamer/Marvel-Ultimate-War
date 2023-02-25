package model.effects;

import model.world.Champion;

public class Silence extends Effect {

	public Silence( int duration) {
		super("Silence", duration, EffectType.DEBUFF);
		
	}
	public String toString() {
		String info="Target cannot use abilities \n"
				+ "Increase max action points per turn and current action points by 2";
		return info;
	}

	@Override
	public void apply(Champion c) {
		c.setCurrentActionPoints(c.getCurrentActionPoints()+2);
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()+2);

		
		
	}

	@Override
	public void remove(Champion c) {
		c.setCurrentActionPoints(c.getCurrentActionPoints()-2);
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()-2);
		
		
	}

}
