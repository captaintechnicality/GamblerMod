package gamblermod.cards.skills;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import gamblermod.enumPatches.AbstractCardEnum;

public class TenDiamonds extends CustomCard {

    public static final String ID = "gamblermod:TenDiamonds";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // Get object containing the strings that are displayed in the game.
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "gamblermod/img/cards/defend.png";
    private static final int COST = 2;
    private static final int COINS = 5;
    private static final int UPGRADE_COINS = 2;

    public TenDiamonds() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.Gambler_CHECKERBOARD,
                CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = COINS;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int skills = -1;
        for(AbstractCard card : AbstractDungeon.player.hand.group)
        {
            if(card.type == CardType.SKILL)
                skills++;
        }
        for(int i = 0; i < skills*magicNumber; i++)
        {
            AbstractDungeon.effectList.add(new GainPennyEffect(p, this.hb.cX, this.hb.cY, p.hb.cX, p.hb.cY, true));
        }

        AbstractDungeon.player.gainGold(skills*magicNumber);
    }

    @Override
    public AbstractCard makeCopy() {
        return new TenDiamonds();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_COINS);
        }
    }
}
