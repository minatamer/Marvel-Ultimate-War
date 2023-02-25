package model.abilities;

import java.util.ArrayList;

import model.world.Damageable;

public  class HealingAbility extends Ability {
	private int healAmount;

	public HealingAbility(String name,int cost, int baseCoolDown, int castRadius, AreaOfEffect area,int required, int healingAmount) {
		super(name,cost, baseCoolDown, castRadius, area,required);
		this.healAmount = healingAmount;
	}

	public int getHealAmount() {
		return healAmount;
	}

	public void setHealAmount(int healAmount) {
		this.healAmount = healAmount;
	}

	public String toString() {
		return "Name: "+ this.getName()+"\n"
				+ "Cost: " + this.getManaCost()+"\n"
				+ "Base Cool Down: " + this.getBaseCooldown()+"\n"
				+ "Current Cool Down: " + this.getCurrentCooldown()+"\n"
				+ "Cast Radius: " + this.getCastRange() +"\n"
				+ "Area of Effect: "+ this.getCastArea() +"\n"
				+ "Required Action Points: "+ this.getRequiredActionPoints()+"\n"
				+ "Healing Amount: " + this.getHealAmount();
		
				
	}
	@Override
	public void execute(ArrayList<Damageable> targets) {
		for (Damageable d : targets)

			d.setCurrentHP(d.getCurrentHP() + healAmount);

	}
	

}
