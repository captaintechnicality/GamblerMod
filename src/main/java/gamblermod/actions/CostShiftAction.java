package gamblermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class CostShiftAction extends AbstractGameAction
{

    private int costShift;
    boolean retrievingCards = false;

    ArrayList<AbstractCard> cannotShift;

    public CostShiftAction(int cost)
    {
        costShift = cost;
        cannotShift = new ArrayList<AbstractCard>();
    }

    @Override
    public void update()
    {
        AbstractPlayer p = AbstractDungeon.player;
        if(!retrievingCards) {
            for(AbstractCard card : p.hand.group)
            {
                if(card.cost <= 0 && costShift < 0)
                {
                    cannotShift.add(card);
                }
            }
            if (cannotShift.size() == p.hand.size())
            {
                this.isDone = true;
            }
            else if(cannotShift.size() == p.hand.size() - 1)
            {
                AbstractCard costCard = null;
                for(AbstractCard card : p.hand.group) {
                    if (card.cost > 0 || costShift > 0)
                        costCard = card;
                }
                ModifyCostForCombat(costCard, costShift);
                isDone = true;
            }
            else
            {
                String reason = "Increase";
                if(costShift < 0)
                    reason = "Decrease";

                p.hand.group.removeAll(cannotShift);
                AbstractDungeon.handCardSelectScreen.open(reason, 1, false, false);
                retrievingCards = true;
            }
        }
        else
        {
            AbstractCard card = AbstractDungeon.handCardSelectScreen.selectedCards.getBottomCard();
            ModifyCostForCombat(card, costShift);
            p.hand.addToHand(card);
            p.hand.group.addAll(cannotShift);
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
            p.hand.refreshHandLayout();
            isDone = true;
        }
    }

    void ModifyCostForCombat(AbstractCard card, int amt)
    {
        if(amt > 0)
        {
            card.updateCost(amt);
        }
        else if(amt < 0)
        {
            if(card.cost == 0)
                return;
            else if(card.cost + amt < 0)
            {
                card.updateCost(amt);
            }
            else
            {
                card.updateCost(amt);
            }
        }
    }
}
