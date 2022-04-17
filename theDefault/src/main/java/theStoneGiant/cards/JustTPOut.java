package theStoneGiant.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theStoneGiant.DefaultMod;
import theStoneGiant.characters.TheDefault;
import theStoneGiant.powers.GrowPower;

import static theStoneGiant.DefaultMod.makeCardPath;

public class JustTPOut extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Just TP Out: 8 block, draw 1 card for each stack of grow.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(JustTPOut.class.getSimpleName());
    public static final String IMG = makeCardPath("tp.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_TINY;

    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int DRAW_PER_GROW = 1;


    // /STAT DECLARATION/


    public JustTPOut() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = DRAW_PER_GROW;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        int grow = 0;
        if(p.hasPower(GrowPower.POWER_ID)){
            grow = p.getPower(GrowPower.POWER_ID).amount;
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(grow * DRAW_PER_GROW));
        }

    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
