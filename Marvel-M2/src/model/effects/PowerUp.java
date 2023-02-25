package model.effects;

import model.abilities.Ability;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;

public class PowerUp extends Effect {

	public PowerUp(int duration) {
		super("PowerUp", duration, EffectType.BUFF);

	}
	public String toString() {
		String info = "Increase damageAmount and  \n healAmount of all damaging and \n healing abilities \n of the target by 20%";
		return info;
	}

	@Override
	public void apply(Champion c) {
		for (Ability a : c.getAbilities())

		{
			if (a instanceof HealingAbility)
				((HealingAbility) a).setHealAmount((int) (((HealingAbility) a).getHealAmount() * 1.2));
			else if (a instanceof DamagingAbility)
				((DamagingAbility) a).setDamageAmount((int) (((DamagingAbility) a).getDamageAmount() * 1.2));
		}

	}
	

	@Override
	public void remove(Champion c) {
		for (Ability a : c.getAbilities()) {
			if (a instanceof HealingAbility)
				((HealingAbility) a).setHealAmount((int) (((HealingAbility) a).getHealAmount() / 1.2));
			else if (a instanceof DamagingAbility)
				((DamagingAbility) a).setDamageAmount((int) (((DamagingAbility) a).getDamageAmount() / 1.2));
		}

	}

}
