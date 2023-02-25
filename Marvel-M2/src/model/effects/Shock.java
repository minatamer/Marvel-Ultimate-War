package model.effects;

import model.world.Champion;

public class Shock extends Effect {

	public Shock(int duration) {
		super("Shock", duration, EffectType.DEBUFF);
		
	}
	public String toString() {
		String info="Decrease target speed by 10% \n"
				+ "Decrease the target’s normal attack damage by 10% \n"
				+ "Decrease max action points per turn and current action points by 1.";
		return info;
	}

	@Override
	public void apply(Champion c) {
		c.setSpeed((int) (c.getSpeed()*0.9));
		c.setAttackDamage((int) (c.getAttackDamage()*0.9));
		c.setCurrentActionPoints(c.getCurrentActionPoints()-1);
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()-1);
		
	}

	@Override
	public void remove(Champion c) {
		c.setSpeed((int) (c.getSpeed()/0.9));
		c.setAttackDamage((int) (c.getAttackDamage()/0.9));
		c.setCurrentActionPoints(c.getCurrentActionPoints()+1);
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()+1);
		
	}

}
