package model.effects;

import model.world.Champion;

public class Embrace extends Effect {

	public Embrace(int duration) {
		super("Embrace", duration, EffectType.BUFF);
	}

	@Override
	public void apply(Champion c) {
		c.setCurrentHP((int) (c.getMaxHP() * 0.2) + c.getCurrentHP());
		c.setMana((int) (c.getMana() * 1.2));
		c.setSpeed((int) (c.getSpeed() * 1.2));
		c.setAttackDamage((int) (c.getAttackDamage() * 1.2));

	}
	public String toString() {
		String info="Permanently add 20% from maxHP to currentHP \n"
				+ "Permanently increase mana by 20% \n"
				+ "Increase speed and attackDamage by 20%";
		return info;
	}

	@Override
	public void remove(Champion c) {
		
		c.setSpeed((int) (c.getSpeed() / 1.2));
		c.setAttackDamage((int) (c.getAttackDamage() / 1.2));

	}

}
