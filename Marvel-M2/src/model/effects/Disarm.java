package model.effects;

import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.DamagingAbility;
import model.world.Champion;

public class Disarm extends Effect {

	public Disarm(int duration) {
		super("Disarm", duration, EffectType.DEBUFF);

	}
	public String toString() {
		String info="Target cannot use normal attacks \n"
				+ "Gain a SINGLETARGET damaging ability called “Punch” \n";
		return info;
				
	}

	public void apply(Champion c) {
		c.getAbilities().add(new DamagingAbility("Punch", 0, 1, 1, AreaOfEffect.SINGLETARGET, 1, 50));
	}

	public void remove(Champion c) {
		for (Ability a : c.getAbilities()) {
			if (a.getName().equals("Punch"))
			{
				c.getAbilities().remove(a);
				break;
			}
		}
	}

}
