package gamblermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.green.Adrenaline;
import com.megacrit.cardcrawl.cards.green.MasterfulStab;
import com.megacrit.cardcrawl.cards.green.Slice;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gamblermod.cards.attacks.ClubPair;
import gamblermod.cards.skills.DiamondPair;
import gamblermod.cards.attacks.HighCard;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;

class CardSorter implements Comparator<Pair<PokerHandCheck.Suit, Integer>>
{
    public int compare(Pair<PokerHandCheck.Suit, Integer> p , Pair<PokerHandCheck.Suit, Integer> q)
    {
        if(p.getKey().ordinal() < q.getKey().ordinal())
            return -1;
        if(p.getKey().ordinal() > q.getKey().ordinal())
            return 1;
        if(p.getValue() < q.getValue())
            return -1;
        if(p.getValue() > q.getValue())
            return 1;
        return 0;
    }
}

public class PokerHandCheck extends AbstractGameAction {

    public enum Suit
    {
        HEARTS,
        CLUBS,
        DIAMONDS,
        SPADES,
        NONE
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        CardGroup hand = player.hand;
        ArrayList<Pair<Suit, Integer>> pokerHand = new ArrayList<>();
        for(AbstractCard card : hand.group)
        {
            Suit pokerSuit;
            switch (card.type)
            {
                case ATTACK:
                    pokerSuit = Suit.CLUBS;
                    break;
                case SKILL:
                    pokerSuit = Suit.DIAMONDS;
                    break;
                case POWER:
                    pokerSuit = Suit.HEARTS;
                    break;
                case CURSE:
                case STATUS:
                    pokerSuit = Suit.SPADES;
                    break;
                default:
                    pokerSuit = Suit.DIAMONDS;
            }

            pokerHand.add(new Pair<>(pokerSuit, card.cost));
        }
        pokerHand.sort(new CardSorter());
        isDone = true;
        if(CheckStraightFlush(pokerHand))
            return;
        if(CheckStraight(pokerHand))
            return;
        if(CheckFourOfAKind(pokerHand))
            return;
        if(CheckFullHouse(pokerHand))
            return;
        if(CheckFlush(pokerHand))
            return;
        if(CheckThreeOfAKind(pokerHand))
            return;
        if(CheckTwoPair(pokerHand))
            return;
        if(CheckPair(pokerHand))
            return;
        CheckHighCard(pokerHand);
    }



    boolean CheckStraightFlush(ArrayList<Pair<Suit, Integer>> hand)
    {
        Suit currentSuit = Suit.NONE;
        int currentValue = 0;
        int currentStraight = 0;
        for(Pair<Suit, Integer> card : hand)
        {
            if(card.getKey() != currentSuit)
            {
                currentValue = card.getValue();
                currentSuit = card.getKey();
                currentStraight = 1;
            }
            else
            {
                if(card.getValue() == currentValue + 1)
                {
                    currentValue++;
                    currentStraight++;
                    if(currentStraight >= 5) {
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Adrenaline()));
                        return true;
                    }
                }
                else if(card.getValue() > currentValue)
                {
                    currentValue = card.getValue();
                    currentStraight = 1;
                }
            }
        }

        return false;
    }

    boolean CheckStraight(ArrayList<Pair<Suit, Integer>> hand)
    {
        int currentValue = Integer.MIN_VALUE;
        int currentStraight = 0;
        for(Pair<Suit, Integer> card : hand)
        {
            if(card.getValue() == currentValue + 1)
            {
                currentValue++;
                currentStraight++;
                if(currentStraight >= 5) {
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new MasterfulStab()));
                    return true;
                }
            }
            else if(card.getValue() > currentValue)
            {
                currentValue = card.getValue();
                currentStraight = 1;
            }
        }

        return false;
    }

    boolean CheckFourOfAKind(ArrayList<Pair<Suit, Integer>> hand)
    {
        Suit currentSuit = Suit.NONE;
        int currentValue = 0;
        int currentDupes = 0;
        for(Pair<Suit, Integer> card : hand)
        {
            if(card.getKey() != currentSuit || card.getValue() != currentValue)
            {
                currentValue = card.getValue();
                currentSuit = card.getKey();
                currentDupes = 1;
            }
            else
            {
                currentDupes++;
                if(currentDupes >= 4)
                {
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new MasterOfStrategy()));
                    return true;
                }
            }
        }

        return false;
    }

    boolean CheckFullHouse(ArrayList<Pair<Suit, Integer>> hand)
    {
        boolean pairMade = false;
        boolean threeMade = false;
        Suit currentSuit = Suit.NONE;
        int currentValue = 0;
        int currentDupes = 0;
        for(Pair<Suit, Integer> card : hand)
        {
            if(currentSuit != card.getKey() || currentValue != card.getValue())
            {
                if(currentDupes == 2)
                {
                    if(threeMade)
                    {
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new DramaticEntrance()));
                        return true;
                    }
                    pairMade = true;
                }
                currentSuit = card.getKey();
                currentValue = card.getValue();
                currentDupes = 1;
            }
            else
            {
                currentDupes++;
                if(currentDupes >= 3)
                {
                    if(pairMade)
                    {
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new DramaticEntrance()));
                        return true;
                    }
                    threeMade = true;
                }
            }
        }

        return false;
    }

    boolean CheckFlush(ArrayList<Pair<Suit, Integer>> hand)
    {
        Suit currentSuit = Suit.NONE;
        int currentDupes = 0;

        for(Pair<Suit, Integer> card : hand)
        {
            if(card.getKey() != currentSuit)
            {
                currentSuit = card.getKey();
                currentDupes = 1;
            }
            else
            {
                currentDupes++;
                if(currentDupes >= 5)
                {
                    AbstractCard rewardCard = new FlashOfSteel();
                    rewardCard.upgrade();
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(rewardCard));
                    return true;
                }
            }
        }

        return false;
    }

    boolean CheckThreeOfAKind(ArrayList<Pair<Suit, Integer>> hand)
    {
        Suit currentSuit = Suit.NONE;
        int currentValue = 0;
        int currentDupes = 0;
        for(Pair<Suit, Integer> card : hand)
        {
            if(card.getKey() != currentSuit || card.getValue() != currentValue)
            {
                currentValue = card.getValue();
                currentSuit = card.getKey();
                currentDupes = 1;
            }
            else
            {
                currentDupes++;
                if(currentDupes >= 3)
                {
                    AbstractCard rewardCard = new Finesse();
                    rewardCard.upgrade();
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(rewardCard));
                    return true;
                }
            }
        }

        return false;
    }

    boolean CheckTwoPair(ArrayList<Pair<Suit, Integer>> hand)
    {
        int pairsMade = 0;
        Suit currentSuit = Suit.NONE;
        int currentValue = 0;
        for(Pair<Suit, Integer> card : hand)
        {
            if(currentSuit != card.getKey() || currentValue != card.getValue())
            {
                currentSuit = card.getKey();
                currentValue = card.getValue();
            }
            else
            {
                pairsMade++;
                if(pairsMade >= 2)
                {
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Slice()));
                    return true;
                }
            }
        }

        return false;
    }

    boolean CheckPair(ArrayList<Pair<Suit, Integer>> hand)
    {
        Suit currentSuit = Suit.NONE;
        int currentValue = 0;
        for(Pair<Suit, Integer> card : hand)
        {
            if(currentSuit != card.getKey() || currentValue != card.getValue())
            {
                currentSuit = card.getKey();
                currentValue = card.getValue();
            }
            else
            {
                switch (currentSuit)
                {
                    case CLUBS:
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new ClubPair(currentValue)));
                        break;
                    default:
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new DiamondPair(currentValue)));
                        break;
                }
                return true;
            }
        }
        return false;
    }

    boolean CheckHighCard(ArrayList<Pair<Suit, Integer>> hand)
    {
        int currentValue = 0;
        for(Pair<Suit, Integer> card : hand)
        {
            if(card.getValue() > currentValue)
                currentValue = card.getValue();
        }

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new HighCard(currentValue)));
        return false;
    }
}
