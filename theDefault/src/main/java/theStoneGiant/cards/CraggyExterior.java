package theStoneGiant.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import theStoneGiant.DefaultMod;
import theStoneGiant.characters.TheDefault;
import theStoneGiant.powers.CraggyExteriorPower;
import theStoneGiant.powers.GrowPower;

import static theStoneGiant.DefaultMod.makeCardPath;

public class CraggyExterior extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Craggy Exterior: Gain X block and Y thorns for each stack of Grow you have
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(CraggyExterior.class.getSimpleName());
    public static final String IMG = makeCardPath("craggy_exterior.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_TINY;

    private static final int COST = 1;

    private static final int BLOCK = 3;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    private static final int THORNS = 2;
    private static final int UPGRADE_PLUS_THORNS = 1;

    // /STAT DECLARATION/


    public CraggyExterior() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = THORNS;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int grow = 0;
        if(p.hasPower(GrowPower.POWER_ID)){
            grow = p.getPower(GrowPower.POWER_ID).amount;
        }
        for(int i = 0; i < grow; i++){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CraggyExteriorPower(p, p, magicNumber)));
        }

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_PLUS_THORNS);
            initializeDescription();
        }
    }
}