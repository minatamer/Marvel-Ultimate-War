package model.abilities;

import java.util.ArrayList;

import model.world.Damageable;

public class DamagingAbility extends Ability {

	private int damageAmount;

	public DamagingAbility(String name, int cost, int baseCoolDown, int castRadius, AreaOfEffect area, int required,
			int damageAmount) {
		super(name, cost, baseCoolDown, castRadius, area, required);
		this.damageAmount = damageAmount;
	}

	public int getDamageAmount() {
		return damageAmount;
	}

	public void setDamageAmount(int damageAmount) {
		this.damageAmount = damageAmount;
	}
	public String toString() {
		return "Name: "+ this.getName()+"\n"
				+ "Cost:" + this.getManaCost()+"\n"
				+ "Base Cool Down: " + this.getBaseCooldown()+"\n"
				+ "Current Cool Down: " + this.getCurrentCooldown()+"\n"
				+ "Cast Radius: " + this.getCastRange() +"\n"
				+ "Area of Effect: "+ this.getCastArea() +"\n"
				+ "Required Action Points: "+ this.getRequiredActionPoints()+"\n"
				+ "Damage Amount: " + this.getDamageAmount();
	}

	@Override
	public void execute(ArrayList<Damageable> targets) {
		for (Damageable d : targets)

			d.setCurrentHP(d.getCurrentHP() - damageAmount);

	}
}
