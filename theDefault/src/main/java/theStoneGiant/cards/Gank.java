package theStoneGiant.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theStoneGiant.DefaultMod;
import theStoneGiant.characters.TheDefault;

import static theStoneGiant.DefaultMod.makeCardPath;

public class Gank extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Gank: Deal X damage. If this is your first attack this turn, deal an additional Y damage.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Gank.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("gank.png");
    // Setting the image as as easy as can possibly be now. You just need to provide the image name
    // and make sure it's in the correct folder. That's all.
    // There's makeCardPath, makeRelicPath, power, orb, event, etc..
    // The list of all of them can be found in the main DefaultMod.java file in the
    // ==INPUT TEXTURE LOCATION== section under ==MAKE IMAGE PATHS==


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_TINY;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int BONUS_DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 3;

    // /STAT DECLARATION/

    public Gank() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseDamage = DAMAGE;
        baseMagicNumber = BONUS_DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Check if we've used an attack already this turn
        boolean attacked = false;
        for(int i = 0; i < AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1; i++){
            if(AbstractDungeon.actionManager.cardsPlayedThisTurn.get(i).type == CardType.ATTACK)
                attacked = true;
        }

        if(!attacked)
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage + magicNumber, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        else
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    //Glow with a gold border if the card will have bonus damage from being the first attack that turn
    @Override
    public void triggerOnGlowCheck() {
        boolean attacked = false;
        for(int i = 0; i < AbstractDungeon.actionManager.cardsPlayedThisTurn.size(); i++){
            if(AbstractDungeon.actionManager.cardsPlayedThisTurn.get(i).type == CardType.ATTACK)
                attacked = true;
        }
        this.glowColor = attacked ? AbstractCard.BLUE_BORDER_GLOW_COLOR : AbstractCard.GOLD_BORDER_GLOW_COLOR;
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
