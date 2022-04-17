package theStoneGiant.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theStoneGiant.powers.TreePower;

//Card Modifier class used to update the description of the Tree Volley card dynamically
public class TreeVolleyMod extends AbstractCardModifier {
    private int treeCount = 0;
    private int damage;
    public TreeVolleyMod(int damage){
        this.damage = damage;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(AbstractDungeon.player == null || AbstractDungeon.monsterList == null)
            return rawDescription;
        if(AbstractDungeon.player.hasPower(TreePower.POWER_ID)){
            treeCount = AbstractDungeon.player.getPower(TreePower.POWER_ID).amount;
        }
        return rawDescription + " NL (Deal " + (damage * treeCount) + " damage total.)";
    }

    @Override
    public AbstractCardModifier makeCopy(){
        return new TreeVolleyMod(damage);
    }
}
