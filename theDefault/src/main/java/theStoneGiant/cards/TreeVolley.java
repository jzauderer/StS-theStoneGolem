package theStoneGiant.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theStoneGiant.DefaultMod;
import theStoneGiant.characters.TheDefault;
import theStoneGiant.powers.GrowPower;
import theStoneGiant.powers.TreePower;

import static theStoneGiant.DefaultMod.makeCardPath;

public class TreeVolley extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Tree Volley: Consumes tree, x dmg x times
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(TreeVolley.class.getSimpleName());
    public static final String IMG = makeCardPath("tree_volley.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_TINY;

    private static final int COST = 2;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_DMG = 2;

    // /STAT DECLARATION/


    public TreeVolley() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int trees = 0;
        if(p.hasPower(TreePower.POWER_ID)){
            trees = p.getPower(TreePower.POWER_ID).amount;
        }

        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage * trees, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SMASH));

        //Remove tree power
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, TreePower.POWER_ID));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DMG);
            initializeDescription();
        }
    }
}