
//attention!! abandoned!!
package muelmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ClearAction extends AbstractGameAction {
    private int amount;

    public ClearAction(int amount) {
        this.amount = amount;
        this.target = AbstractDungeon.player;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;

        if (this.isDone) {
            return;
        }

        for (AbstractCard card : player.hand.group) {
            addToTop(new DiscardSpecificCardAction(card, player.hand));
            addToTop(new GainBlockAction(player, player, this.amount));
        }

        this.isDone = true;
    }
}