package theStoneGiant.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect;
import theStoneGiant.DefaultMod;
import theStoneGiant.actions.SettleQuarryAction;
import theStoneGiant.characters.TheDefault;
import theStoneGiant.powers.GrowPower;

import static theStoneGiant.DefaultMod.makeCardPath;

public class SettleQuarry extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Settle Quarry: Deal 6 damage for each stack of Grow you have. If Fatal, heal 4. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(SettleQuarry.class.getSimpleName());
    public static final String IMG = makeCardPath("settle.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_TINY;

    private static final int COST = 2;
    private static final int DAMAGE = 6;
    private static final int HEAL = 4;

    // /STAT DECLARATION/

    public SettleQuarry() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseHeal = HEAL;
        magicNumber = baseMagicNumber = HEAL;

        isMultiDamage = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        /*  This code has been relocated/re-formatted in the SettleQuarryAction class
        // Create an int which equals to your current stacks of Grow
        int grow = 0;
        if(p.hasPower(GrowPower.POWER_ID)){
            grow = p.getPower(GrowPower.POWER_ID).amount;
        }

        // For each grow, create 1 damage action.
        for (int i = 0; i < grow; i++) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }

        // Heal on fatal
        if((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion"))
            p.heal(HEAL);

        // Exhaust if un-upgraded
        if(!upgraded){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, p.hand));
        }
        */

        AbstractDungeon.actionManager.addToBottom(new SettleQuarryAction(p, m, new DamageInfo(p, damage, damageTypeForTurn),
                HEAL, upgraded, this));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}