package theStoneGiant.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theStoneGiant.powers.CommonPower;
import theStoneGiant.powers.GrowPower;

public class SettleQuarryAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final int heal;
    private final DamageInfo info;
    private int grow = 0;
    private float myDuration = 0.0F;

    public SettleQuarryAction(final AbstractPlayer p, final AbstractMonster target, DamageInfo info, int healAmount, boolean upgraded, AbstractCard card) {
        this.info = info;
        this.setValues(target, info);
        this.heal = healAmount;
        this.p = p;
        if(!upgraded)
            card.exhaust = true;
        if(p.hasPower(GrowPower.POWER_ID)){
            grow = p.getPower(GrowPower.POWER_ID).amount;
        }
        this.startDuration = Settings.ACTION_DUR_FASTER;
        this.duration = this.startDuration * grow;
    }

    @Override
    public void update() {
        myDuration -= Gdx.graphics.getDeltaTime();
        if (myDuration <= 0 && grow > 0) {
            myDuration = startDuration;
            this.target.damage(this.info);
            grow--;
            if(grow <= 0){
                if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.hasPower("Minion")) {
                    p.heal(heal);
                }
            }
        }

        tickDuration();
    }
}
