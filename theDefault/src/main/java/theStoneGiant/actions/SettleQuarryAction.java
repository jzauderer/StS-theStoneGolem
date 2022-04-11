package theStoneGiant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theStoneGiant.powers.CommonPower;
import theStoneGiant.powers.GrowPower;

public class SettleQuarryAction extends AbstractGameAction {
    private AbstractPlayer p;
    private final int heal;
    private final DamageInfo info;
    private boolean upgraded;
    private AbstractCard card;
    private static final float delay = 0.1F;

    public SettleQuarryAction(final AbstractPlayer p, final AbstractMonster target, DamageInfo info, int healAmount, boolean upgraded, AbstractCard card) {
        this.info = info;
        this.setValues(target, info);
        this.heal = healAmount;
        this.upgraded = upgraded;
        this.p = p;
    }

    @Override
    public void update() {
        // Create an int which equals to your current stacks of Grow
        int grow = 0;
        if(p.hasPower(GrowPower.POWER_ID)){
            grow = p.getPower(GrowPower.POWER_ID).amount;
        }

        // For each grow, create 1 damage action.
        for (int i = 0; i < grow; i++) {
            this.target.damage(this.info);
            this.tickDuration();
        }

        //If fatal, heal
        if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")){
            p.heal(heal);
        }

        // Exhaust if un-upgraded
        //TODO: Make this actually exhaust
        if(!upgraded){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, p.hand));
        }
    }
}
