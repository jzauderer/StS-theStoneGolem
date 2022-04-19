package theStoneGiant.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theStoneGiant.DefaultMod;
import theStoneGiant.characters.TheDefault;
import theStoneGiant.powers.GrowPower;

import static theStoneGiant.DefaultMod.makeCardPath;
import static theStoneGiant.DefaultMod.makeID;

public class GrowCard extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Grow: Innate. Gain 1 stack of Grow.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(GrowCard.class.getSimpleName());
    public static final String IMG = makeCardPath("tony.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_TINY;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;

    private int AMOUNT = 1;

    private int GROW_POTENCY = 2;


    // /STAT DECLARATION/


    public GrowCard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = AMOUNT;
        isInnate = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, GROW_POTENCY)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, GROW_POTENCY)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GrowPower(p, p, 1)));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
