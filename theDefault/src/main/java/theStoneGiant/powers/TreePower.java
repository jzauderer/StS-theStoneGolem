package theStoneGiant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theStoneGiant.DefaultMod;
import theStoneGiant.util.TextureLoader;

import static theStoneGiant.DefaultMod.makePowerPath;

//Gain 1 stack of grow.

public class TreePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("TreePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("tree_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("tree_power32.png"));

    public TreePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    //Cut damage done when wielding a tree
    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        //We're only modifying NORMAL type damage, so just return if it's not that
        if(!type.equals(DamageInfo.DamageType.NORMAL))
            return super.atDamageGive(damage, type);
        //This won't affect strength bonus, so we subtract that before cutting the damage, then re-add it
        int str = 0;
        if(owner.hasPower(StrengthPower.POWER_ID)){
            str = owner.getPower(StrengthPower.POWER_ID).amount;
        }
        damage -= str;
        damage *= 0.5F;
        damage += str;
        return super.atDamageGive(damage, type);
    }

    //Remove a charge of Tree after using an attack
    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(card.type == AbstractCard.CardType.ATTACK){
            if(owner.getPower(TreePower.POWER_ID).amount > 1)
                owner.addPower(new TreePower(owner, owner, -1));
            else
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, TreePower.POWER_ID));
        }
        super.onAfterUseCard(card, action);
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new TreePower(owner, source, amount);
    }
}
