package muelmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardSkillAction extends AbstractGameAction {

    public DiscardSkillAction(int the_amount) {
        this.target = AbstractDungeon.player;
        this.actionType = AbstractGameAction.ActionType.DISCARD;
        this.amount = the_amount;
    }

    public void update() {
        if (this.isDone) {
            return;
        }

        for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if(card.type == CardType.SKILL){
                    addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.hand));
                    this.addToBot(new GainBlockAction(this.target, this.target, this.amount));
                }
               
        }

        this.isDone = true;
    }
}