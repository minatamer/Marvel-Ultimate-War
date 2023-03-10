package model.abilities;

import java.util.ArrayList;

import model.effects.Effect;
import model.world.Champion;
import model.world.Damageable;

public class CrowdControlAbility extends Ability {
	private Effect effect;

	public CrowdControlAbility(String name, int cost, int baseCoolDown, int castRadius, AreaOfEffect area, int required,
			Effect effect) {
		super(name, cost, baseCoolDown, castRadius, area, required);
		this.effect = effect;

	}

	public Effect getEffect() {
		return effect;
	}
	public String toString() {
		return "Name: "+ this.getName()+"\n"
				+ "Cost: " + this.getManaCost()+"\n"
				+ "Base Cool Down: " + this.getBaseCooldown()+"\n"
				+ "Current Cool Down: " + this.getCurrentCooldown()+"\n"
				+ "Cast Radius: " + this.getCastRange() +"\n"
				+ "Area of Effect: "+ this.getCastArea() +"\n"
				+ "Required Action Points: "+ this.getRequiredActionPoints()+"\n"
				+ "Effect: " + this.getEffect().toString();}
	
	

	@Override
	public void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException {
		for(Damageable d: targets)
		{
			Champion c =(Champion) d;
			c.getAppliedEffects().add((Effect) effect.clone());
			effect.apply(c);
		}
		
	}

}
