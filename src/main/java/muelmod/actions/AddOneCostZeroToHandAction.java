package muelmod.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AddOneCostZeroToHandAction extends AbstractGameAction {
    private AbstractPlayer p;

    public AddOneCostZeroToHandAction() {
        this.p = AbstractDungeon.player;
        setValues((AbstractCreature)this.p, (AbstractCreature)AbstractDungeon.player, this.amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.p.discardPile.size() > 0) {
            for (AbstractCard card : this.p.discardPile.group) {
                if (card.cost == 0 || card.freeToPlayOnce) {
                    addToBot((AbstractGameAction)new DiscardToHandAction(card));
                    break; // 只添加一张牌
                }
            }
        }
        tickDuration();
        this.isDone = true;
    }
}