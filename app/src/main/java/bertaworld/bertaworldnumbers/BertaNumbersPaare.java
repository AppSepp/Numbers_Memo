package bertaworld.bertaworldnumbers;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import bertaworld.mylibrary.AutoFitText;

public class BertaNumbersPaare extends Activity {

    public int SpracheNummer, AnzSprachen;
    public int SpielNummer, AnzZeilen, AnzSpalten, AnzKarten, AnzPaare, AnzOffene;
    public int KlickNummer, KlickZeile, KlickSpalte;
    public int Offene_1, Offene_2;
    public FrameLayout FOffen_1, FOffen_2;
    public int PaarNummer[], KartenStatus[], KartenInhalt[];
    public CustomListAdapter EinstellungenAdapter, SpracheAdapter, SpielAdapter;
    public int QBild[];
    public Integer EinstellungenBildItem[], SpracheBildItem[], SpielBildItem[], EinstellungenTextKolor[], SpracheTextKolor[], SpielTextKolor[];
    public String QText[][], EinstellungenTextItem[], SpracheTextItem[], SpielTextItem[];
    public float EinstellungenItemDurchsichtig[], SpracheItemDurchsichtig[], SpielItemDurchsichtig[];
    public String QSpruch[][];
    public boolean Schreiben, Sprechen, Einstellungen_an, Sprache_an, Spiel_an;
    public Animator animkarte_1, animkarte_2, animkarte_3, animkarte_4, animkarte_groesser, animkarte_kleiner;
    public Animator animinhalt_2, animinhalt_3, animinhalt_groesser, animinhalt_kleiner;
    public Animator Einstellungen_zeigen, Einstellungen_ausblenden, Sprache_zeigen, Sprache_ausblenden, Spiel_zeigen, Spiel_ausblenden;
    public AnimatorSet AS_karteauf, AS_kartezu;
    public AutoFitText Schreibbereich, TextEinstellungen, TextSprache, TextSpiel;
    public LinearLayout Spielbereich;
    public LinearLayout LL_Zeile[], Einstellungen, EinstellungenSprache, EinstellungenSpiel;
    public LinearLayout.LayoutParams LLparam;
    public FrameLayout FL_Karte[][];
    public ImageView Deckblatt[][];
    public ImageView Karte[][];
    public ImageView Inhalt[][];
    public Typeface type;
    public ListView LVEinstellungen, LVSprache, LVSpiel;
    public ImageView OWL, Flagge;
    public AnimationDrawable OWL_anim;
    public TextToSpeech Vorleser;
    public Locale l_en, l_de, l_pl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berta_numbers_paare);

        AnzSprachen = 0;

        //DBHandlerInhalt db = new DBHandlerInhalt(this);
        //db.deleteTab();
        //db.createTab();

        Schreibbereich = (AutoFitText) findViewById(R.id.textView2);

        //Log.d("Insert: ", "Inserting ..");
        //db.addShop(new Shop(1, "Dockers", " 475 Brannan St #330, San Francisco, CA 94107, United States"));
        //db.addShop(new Shop(2, "Dunkin Donuts", "White Plains, NY 10601"));
        //db.deleteShop(db.getShop(2));
        //db.deleteShop(db.getShop(11));
        //db.deleteDB(this);
        //db.deleteShop(db.getShop(1));

        //db.addShop(new Shop(3, "Pizza Porlar", "North West Avenue, Boston , USA"));
        //db.addShop(new Shop(4, "Town Bakers", "Beverly Hills, CA 90210, USA"));
        //int Anz = db.getInhaltCount();
// Reading all shops
        //Log.d("Reading: ", "Reading all shops..");
        //List<Shop> shops = db.getAllShops();

        //for (Shop shop : shops) {
        //    String log = "Id: " + shop.getId() + " ,Name: " + Anz + " ,Address: " + shop.getAddress();
// Writing shops to log
            //Log.d("Shop: : ", log);
        //    Schreibbereich.setText(String.valueOf(Anz));


    //}


        Komponenten_aufbauen();
        Param_definieren();
        Spiel_definieren();
        Spiel_starten();
    }

    public void Spiel_starten() {
        Spielbereich_aufbauen();
        Paare_festlegen();
        Inhalte_festlegen();
        Inhalte_laden();
    }

    public void Spielende() {
    } //Spiel_starten();


    public void Komponenten_aufbauen() {

        type = Typeface.createFromAsset(getAssets(), "fonts/segoesc.ttf");

        Schreibbereich = (AutoFitText) findViewById(R.id.textView2);
        Schreibbereich.setTypeface(type);

        Spielbereich = (LinearLayout) findViewById(R.id.Spielbereich);

        TextEinstellungen = (AutoFitText) findViewById(R.id.TVEinstellungen);
        TextEinstellungen.setTypeface(type);
        TextEinstellungen.setTextColor(Color.YELLOW);

        TextSprache = (AutoFitText) findViewById(R.id.TVEinstellungenSprache);
        TextSprache.setTypeface(type);
        TextSprache.setTextColor(Color.YELLOW);

        TextSpiel = (AutoFitText) findViewById(R.id.TVEinstellungenSpiel);
        TextSpiel.setTypeface(type);
        TextSpiel.setTextColor(Color.YELLOW);

        Einstellungen = (LinearLayout) findViewById(R.id.Einstellungen);
        EinstellungenSprache = (LinearLayout) findViewById(R.id.EinstellungenSprache);
        EinstellungenSpiel = (LinearLayout) findViewById(R.id.EinstellungenSpiel);

        LLparam = new LinearLayout.LayoutParams(0, 0, 1);
        LVEinstellungen = (ListView) findViewById(R.id.LVEinstellungen);
        LVEinstellungen.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Einstellungen_geklickt(position);
            }
        });
        LVSprache = (ListView) findViewById(R.id.LVSprache);
        LVSpiel = (ListView) findViewById(R.id.LVSpiel);

        EinstellungenTextItem = new String[AnzSprachen + 1];
        EinstellungenBildItem = new Integer[AnzSprachen + 1];
        EinstellungenTextKolor = new Integer[AnzSprachen + 1];
        EinstellungenItemDurchsichtig = new float[AnzSprachen + 1];
        EinstellungenListe_aktualisieren(1);

        SpracheTextItem = new String[] {"Englisch","Deutsch","Polnisch"}; // ,"Französisch","Italienisch","Russisch","Schwedisch","Spanisch","Türkisch"};
        SpracheBildItem = new Integer[] {R.drawable.gb, R.drawable.de, R.drawable.pl};  // , R.drawable.fr, R.drawable.it, R.drawable.ru,R.drawable.es, R.drawable.es, R.drawable.tr};
        SpracheTextKolor = new Integer[] {Color.BLACK, Color.BLACK, Color.BLACK};  // , Color.BLACK, Color.BLACK, Color.BLACK,Color.BLACK, Color.BLACK, Color.BLACK};
        SpracheItemDurchsichtig = new float[] {0.7f, 0.7f, 0.7f};  // , 0.7f, 0.7f, 0.7f, 0.7f, 0.7f, 0.7f};
        SpracheAdapter = new CustomListAdapter(this, SpracheTextItem, SpracheBildItem, SpracheTextKolor, SpracheItemDurchsichtig);
        LVSprache.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Sprache_geklickt(position);
            }
        });

        SpielTextItem = new String[] {"2 x 4","2 x 6","3 x 6","3 x 8"};
        SpielBildItem = new Integer[] {R.drawable.level_01, R.drawable.level_02, R.drawable.level_03, R.drawable.level_04};
        SpielTextKolor = new Integer[] {Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK};
        SpielItemDurchsichtig = new float[] {0.7f, 0.7f, 0.7f, 0.7f};
        SpielAdapter = new CustomListAdapter(this, SpielTextItem, SpielBildItem, SpielTextKolor, SpielItemDurchsichtig);
        LVSpiel.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Spiel_geklickt(position);
            }
        });

        OWL = (ImageView) findViewById(R.id.owl);
        OWL_anim = (AnimationDrawable) OWL.getDrawable();
        l_en = new Locale("eng", "", "");
        l_de = new Locale("deu", "", "");
        l_pl = new Locale("pol", "", "");

        Einstellungen_ausblenden = AnimatorInflater.loadAnimator(this, R.animator.layout_ausblenden);
        Einstellungen_zeigen = AnimatorInflater.loadAnimator(this, R.animator.layout_zeigen);
        Einstellungen_ausblenden.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Einstellungen.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        Einstellungen_zeigen.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        Sprache_ausblenden = AnimatorInflater.loadAnimator(this, R.animator.layout_ausblenden);
        Sprache_zeigen = AnimatorInflater.loadAnimator(this, R.animator.layout_zeigen);
        Sprache_ausblenden.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Einstellungen.setVisibility(View.VISIBLE);
                //LVEinstellungen.setAdapter(EinstellungenAdapter);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                EinstellungenSprache.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        Sprache_zeigen.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Einstellungen.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        Spiel_ausblenden = AnimatorInflater.loadAnimator(this, R.animator.layout_ausblenden);
        Spiel_zeigen = AnimatorInflater.loadAnimator(this, R.animator.layout_zeigen);
        Spiel_ausblenden.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Einstellungen.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                EinstellungenSpiel.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        Spiel_zeigen.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Einstellungen.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animkarte_1 = AnimatorInflater.loadAnimator(this, R.animator.kartedrehen_1);
        animkarte_2 = AnimatorInflater.loadAnimator(this, R.animator.kartedrehen_2);
        animkarte_3 = AnimatorInflater.loadAnimator(this, R.animator.kartedrehen_3);
        animkarte_4 = AnimatorInflater.loadAnimator(this, R.animator.kartedrehen_4);
        animkarte_groesser = AnimatorInflater.loadAnimator(this, R.animator.kartevergroessern);
        animkarte_kleiner = AnimatorInflater.loadAnimator(this, R.animator.karteverkleinern);
        animinhalt_2 = AnimatorInflater.loadAnimator(this, R.animator.kartedrehen_2);
        animinhalt_groesser = AnimatorInflater.loadAnimator(this, R.animator.kartevergroessern);
        animinhalt_3 = AnimatorInflater.loadAnimator(this, R.animator.kartedrehen_3);
        animinhalt_kleiner = AnimatorInflater.loadAnimator(this, R.animator.karteverkleinern);

        AS_karteauf = new AnimatorSet();
        AS_karteauf.play(animkarte_1);
        AS_karteauf.play(animkarte_kleiner).with(animkarte_1);
        AS_karteauf.play(animkarte_2).after(animkarte_1);
        AS_karteauf.play(animkarte_groesser).after(animkarte_1);
        AS_karteauf.play(animinhalt_2).after(animkarte_1);
        AS_karteauf.play(animinhalt_groesser).after(animkarte_1);

        AS_kartezu = new AnimatorSet();
        AS_kartezu.play(animkarte_3);
        AS_kartezu.play(animkarte_kleiner).with(animkarte_3);
        AS_kartezu.play(animinhalt_3).with(animkarte_3);
        AS_kartezu.play(animinhalt_kleiner).with(animkarte_3);
        AS_kartezu.play(animkarte_4).after(animkarte_3);
        AS_kartezu.play(animkarte_groesser).after(animkarte_3);

        QBild = new int[21];
        QText = new String[21][4];
        QSpruch = new String[2][4];

        QBild[0] = R.drawable.fr_00;
        QBild[1] = R.drawable.fr_01;
        QBild[2] = R.drawable.fr_02;
        QBild[3] = R.drawable.fr_03;
        QBild[4] = R.drawable.fr_04;
        QBild[5] = R.drawable.fr_05;
        QBild[6] = R.drawable.f06;
        QBild[7] = R.drawable.f07;
        QBild[8] = R.drawable.f08;
        QBild[9] = R.drawable.f09;
        QBild[10] = R.drawable.fr_10;
        QBild[11] = R.drawable.f11;
        QBild[12] = R.drawable.f12;
        QBild[13] = R.drawable.f13;
        QBild[14] = R.drawable.f14;
        QBild[15] = R.drawable.f15;
        QBild[16] = R.drawable.f16;
        QBild[17] = R.drawable.f17;
        QBild[18] = R.drawable.f18;
        QBild[19] = R.drawable.f19;
        QBild[20] = R.drawable.f20;

        QSpruch[0][1] = "Great! Pair!";
        QSpruch[0][2] = "Super! Pärchen!";
        QSpruch[0][3] = "Świetnie! Para!";
        QSpruch[1][1] = "Game Over";
        QSpruch[1][2] = "Spielende";
        QSpruch[1][3] = "Koniec gry";

        QText[0][1] = "ZERO";
        QText[0][2] = "NULL";
        QText[0][3] = "ZERO";
        QText[1][1] = "ONE";
        QText[1][2] = "EINS";
        QText[1][3] = "JEDEN";
        QText[2][1] = "TWO";
        QText[2][2] = "ZWEI";
        QText[2][3] = "DWA";
        QText[3][1] = "THREE";
        QText[3][2] = "DREI";
        QText[3][3] = "TRZY";
        QText[4][1] = "FOUR";
        QText[4][2] = "VIER";
        QText[4][3] = "CZTERY";
        QText[5][1] = "FIVE";
        QText[5][2] = "FÜNF";
        QText[5][3] = "PIĘĆ";
        QText[6][1] = "SIX";
        QText[6][2] = "SECHS";
        QText[6][3] = "SZEŚĆ";
        QText[7][1] = "SEVEN";
        QText[7][2] = "SIEBEN";
        QText[7][3] = "SIEDEM";
        QText[8][1] = "EIGHT";
        QText[8][2] = "ACHT";
        QText[8][3] = "OSIEM";
        QText[9][1] = "NINE";
        QText[9][2] = "NEUN";
        QText[9][3] = "DZIEWIĘĆ";
        QText[10][1] = "TEN";
        QText[10][2] = "ZEHN";
        QText[10][3] = "DZIESIĘĆ";
        QText[11][1] = "ELEVEN";
        QText[11][2] = "ELF";
        QText[11][3] = "JEDENAŚCIE";
        QText[12][1] = "TWELVE";
        QText[12][2] = "ZWÖLF";
        QText[12][3] = "DWANAŚCIE";
        QText[13][1] = "THIRTEEN";
        QText[13][2] = "DREIZEHN";
        QText[13][3] = "TRZYNAŚCIE";
        QText[14][1] = "FOURTEEN";
        QText[14][2] = "VIERZEHN";
        QText[14][3] = "CZTERNAŚCIE";
        QText[15][1] = "FIFTEEN";
        QText[15][2] = "FÜNFZEHN";
        QText[15][3] = "PIĘTNAŚCIE";
        QText[16][1] = "SIXTEEN";
        QText[16][2] = "SECHZEHN";
        QText[16][3] = "SZESNAŚCIE";
        QText[17][1] = "SEVENTEEN";
        QText[17][2] = "SIEBZEHN";
        QText[17][3] = "SIEDEMNAŚCIE";
        QText[18][1] = "EIGHTEEN";
        QText[18][2] = "ACHTZEHN";
        QText[18][3] = "OSIEMNAŚCIE";
        QText[19][1] = "NINETEEN";
        QText[19][2] = "NEUNZEHN";
        QText[19][3] = "DZIEWIĘTNAŚCIE";
        QText[20][1] = "TWENTY";
        QText[20][2] = "ZWANZIG";
        QText[20][3] = "DWADZIEŚCIA";
    }

    public void Param_definieren() {

        Schreibbereich.setTypeface(type);
        Schreiben = true;
        Sprechen = true;
        SpracheNummer = 1;
        SpielNummer = 4;
        AnzPaare = 0;
        AnzOffene = 0;
        LVEinstellungen.setAdapter(EinstellungenAdapter);
        LVEinstellungen.setSelection(0);

        SpracheItemDurchsichtig[SpracheNummer - 1] = 1;
        SpracheTextKolor[SpracheNummer - 1] = Color.WHITE;
        LVSprache.setAdapter(SpracheAdapter);
        LVSprache.setSelection(SpracheNummer - 1);

        SpielItemDurchsichtig[SpielNummer - 1] = 1;
        SpielTextKolor[SpielNummer - 1] = Color.WHITE;
        LVSpiel.setAdapter(SpielAdapter);
        LVSpiel.setSelection(SpielNummer - 1);

        Einstellungen_an = false;
        Sprache_an = false;
        Spiel_an = false;

        Vorleser = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {if (status != TextToSpeech.ERROR) {
                    if (SpracheNummer == 1) {
                        Vorleser.setLanguage(l_en);
                        Vorlesen_jetzt("Hi. It´s me.");
                    }
                    if (SpracheNummer == 2) {
                        Vorleser.setLanguage(l_de);
                        Vorlesen_jetzt("Hallo. Hier bin ich.");
                    }
                    if (SpracheNummer == 3) {
                        Vorleser.setLanguage(l_pl);
                        Vorlesen_jetzt("Witam. To ja.");
                    }
                }
            }
        });
    }

    public void Spiel_definieren() {

        if (SpielNummer == 1) {
            AnzKarten = 8;
            AnzZeilen = 2;
            AnzSpalten = 4;
            return;
        }
        if (SpielNummer == 2) {
            AnzKarten = 12;
            AnzZeilen = 2;
            AnzSpalten = 6;
            return;
        }
        if (SpielNummer == 3) {
            AnzKarten = 18;
            AnzZeilen = 3;
            AnzSpalten = 6;
            return;
        }
        if (SpielNummer == 4) {
            AnzKarten = 24;
            AnzZeilen = 3;
            AnzSpalten = 8;
            return;
        }
    }

    public void Spielbereich_aufbauen() {
        Spielbereich.removeAllViews();
        LL_Zeile = new LinearLayout[AnzZeilen + 1];
        FL_Karte = new FrameLayout[AnzSpalten + 1][AnzZeilen + 1];
        Deckblatt = new ImageView[AnzSpalten + 1][AnzZeilen + 1];
        Karte = new ImageView[AnzSpalten + 1][AnzZeilen + 1];
        Inhalt = new ImageView[AnzSpalten + 1][AnzZeilen + 1];
        for (int y = 1; y <= AnzZeilen; y = y + 1) {
            LL_Zeile[y] = new LinearLayout(this);
            LL_Zeile[y].setOrientation(LinearLayout.HORIZONTAL);
            LLparam.weight = 1;
            LLparam.height = FrameLayout.LayoutParams.MATCH_PARENT;
            LLparam.width = FrameLayout.LayoutParams.MATCH_PARENT;
            Spielbereich.addView(LL_Zeile[y]);
            LL_Zeile[y].setLayoutParams(LLparam);
            for (int x = 1; x <= AnzSpalten; x = x + 1) {
                FL_Karte[x][y] = new FrameLayout(this);
                FL_Karte[x][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Karte_klick(view);
                    }
                });
                Deckblatt[x][y] = new ImageView(this);
                Karte[x][y] = new ImageView(this);
                Inhalt[x][y] = new ImageView(this);
                Deckblatt[x][y].setAdjustViewBounds(true);
                Karte[x][y].setAdjustViewBounds(true);
                Inhalt[x][y].setAdjustViewBounds(true);
                Karte[x][y].setRotationY(90);
                Inhalt[x][y].setRotationY(90);
                LLparam.weight = 1;
                LLparam.height = FrameLayout.LayoutParams.MATCH_PARENT;
                LLparam.width = FrameLayout.LayoutParams.MATCH_PARENT;
                FL_Karte[x][y].setTag((y - 1) * AnzSpalten + x);
                LL_Zeile[y].addView(FL_Karte[x][y]);
                FL_Karte[x][y].setLayoutParams(LLparam);
                FL_Karte[x][y].addView(Deckblatt[x][y]);
                FL_Karte[x][y].addView(Karte[x][y]);
                FL_Karte[x][y].addView(Inhalt[x][y]);
                Deckblatt[x][y].setImageResource(R.drawable.deckblatt_01);
                Karte[x][y].setImageResource(R.drawable.karte);
            }
        }
    }

    public void Paare_festlegen() {
        KartenStatus = new int[AnzKarten + 1];
        PaarNummer = new int[AnzKarten + 1];
        for (int nr = 1; nr <= AnzKarten; nr = nr + 1) {
            KartenStatus[nr] = 0;
            PaarNummer[nr] = 0;
        }
        int a, z;
        Random rand = new Random();
        boolean Aus;

        for (int nr = 1; nr <= AnzKarten; nr = nr + 1) {
            if (KartenStatus[nr] == 0) {
                KartenStatus[nr] = 1;
                a = rand.nextInt(AnzKarten - nr) + 1 + nr;
                z = 1;
                Aus = false;
                while (Aus == false) {
                    if (KartenStatus[a] == 0) {
                        KartenStatus[a] = 1;
                        PaarNummer[nr] = a;
                        PaarNummer[a] = nr;
                        Aus = true;
                    }
                    a = a + 1;
                    if (a > AnzKarten) {a = 1;}
                    z = z + 1;
                    if (z > AnzKarten) {Aus = true;}
                }
            }
        }
    }

    public void Inhalte_festlegen() {
        KartenInhalt = new int[AnzKarten + 1];
        for (int nr = 1; nr <= AnzKarten; nr = nr + 1) {
            KartenInhalt[nr] = 999;
        }
        for (int nr = 1; nr <= AnzKarten; nr = nr + 1) {
            if (KartenStatus[nr] == 1) {
                KartenStatus[nr] = 2;
                KartenInhalt[nr] = Finde_Inhalt();
                KartenStatus[PaarNummer[nr]] = 2;
                KartenInhalt[PaarNummer[nr]] = KartenInhalt[nr];
            }
        }
    }

    public void Inhalte_laden() {
        int nr = 0;
        for (int y = 1; y <= AnzZeilen; y = y + 1) {
            for (int x = 1; x <= AnzSpalten; x = x + 1) {
                nr = (y - 1) * AnzSpalten + x;
                Inhalt[x][y].setImageResource(QBild[KartenInhalt[nr]]);
            }
        }
        String text = "";
        Schreibbereich.setText(text);
    }

    private int Finde_Inhalt() {
        int a = 0;
        boolean aus = false;
        while (aus == false) {
            boolean abbruch = false;
            Random rand = new Random();
            a = rand.nextInt(21); // PRÜFUNG OB INHALT SCHON VERGEBEN EINBAUEN
            for (int nr = 1; nr <= AnzKarten; nr = nr + 1) {
                if (a == KartenInhalt[nr]) {
                    abbruch = true;
                }
            }
            if (abbruch == false) {
                aus = true;
            }
        }
        return a;
    }

    private void Karte_klick(View view) {
        Karte_finden(view);
        if (KartenStatus[KlickNummer] == 2) {
            if (AnzOffene == 0) {
                AnzOffene = 1;
                Offene_1 = KlickNummer;
                FOffen_1 = FL_Karte[KlickSpalte][KlickZeile];
                KartenStatus[KlickNummer] = 3;
                Karte_aufdrehen(view);
                Vorlesen_jetzt(QText[KartenInhalt[KlickNummer]][SpracheNummer]);
                String text = QText[KartenInhalt[KlickNummer]][SpracheNummer];
                Schreibbereich.setText(text);
                return;
            }
            if (AnzOffene == 1) {
                AnzOffene = 2;
                Offene_2 = KlickNummer;
                FOffen_2 = FL_Karte[KlickSpalte][KlickZeile];
                KartenStatus[KlickNummer] = 3;
                Karte_aufdrehen(view);
                Vorlesen_jetzt(QText[KartenInhalt[KlickNummer]][SpracheNummer]);
                String text = QText[KartenInhalt[KlickNummer]][SpracheNummer];
                Schreibbereich.setText(text);
                if (PaarNummer[KlickNummer] == Offene_1)  {
                    KartenStatus[KlickNummer] = 4;
                    KartenStatus[Offene_1] = 4;
                    AnzPaare = AnzPaare + 2;
                    AnzOffene = 0;
                    Vorlesen_später(QSpruch[0][SpracheNummer]);
                    if (AnzPaare == AnzKarten) {
                        Vorlesen_später(QSpruch[1][SpracheNummer]);
                        AnzPaare = 0;
                        Spielende();
                    }
                }
                return;
            }
            if (AnzOffene == 2) {
                KartenStatus[Offene_1] = 2;
                Karte_zudrehen(FOffen_1);
                KartenStatus[Offene_2] = 2;
                Karte_zudrehen(FOffen_2);
                Karte_finden(view);
                AnzOffene = 1;
                Offene_1 = KlickNummer;
                FOffen_1 = FL_Karte[KlickSpalte][KlickZeile];
                KartenStatus[KlickNummer] = 3;
                Karte_aufdrehen(view);
                Vorlesen_jetzt(QText[KartenInhalt[KlickNummer]][SpracheNummer]);
                String text = QText[KartenInhalt[KlickNummer]][SpracheNummer];
                Schreibbereich.setText(text);
                return;
            }
        }
        if (KartenStatus[KlickNummer] == 3) {
            Vorlesen_jetzt(QText[KartenInhalt[KlickNummer]][SpracheNummer]);
            String text = QText[KartenInhalt[KlickNummer]][SpracheNummer];
            Schreibbereich.setText(text);
            return;
            }
        if (KartenStatus[KlickNummer] == 4) {
            Vorlesen_jetzt(QText[KartenInhalt[KlickNummer]][SpracheNummer]);
            String text = QText[KartenInhalt[KlickNummer]][SpracheNummer];
            Schreibbereich.setText(text);
            return;
        }
}

    private void Karte_aufdrehen(View view) {
        AS_karteauf.end();
        AS_kartezu.end();
        Karte_finden(view);
        animkarte_1.setTarget(Deckblatt[KlickSpalte][KlickZeile]);
        animkarte_kleiner.setTarget(Deckblatt[KlickSpalte][KlickZeile]);
        animkarte_2.setTarget(Karte[KlickSpalte][KlickZeile]);
        animkarte_groesser.setTarget(Karte[KlickSpalte][KlickZeile]);
        animinhalt_2.setTarget(Inhalt[KlickSpalte][KlickZeile]);
        animinhalt_groesser.setTarget(Inhalt[KlickSpalte][KlickZeile]);
        AS_karteauf.start();
    }

    private void Karte_zudrehen(View view) {
        AS_karteauf.end();
        AS_kartezu.end();
        Karte_finden(view);
        animkarte_3.setTarget(Karte[KlickSpalte][KlickZeile]);
        animkarte_kleiner.setTarget(Karte[KlickSpalte][KlickZeile]);
        animinhalt_3.setTarget(Inhalt[KlickSpalte][KlickZeile]);
        animinhalt_kleiner.setTarget(Inhalt[KlickSpalte][KlickZeile]);
        animkarte_4.setTarget(Deckblatt[KlickSpalte][KlickZeile]);
        animkarte_groesser.setTarget(Deckblatt[KlickSpalte][KlickZeile]);
        AS_kartezu.start();
    }

    private void Karte_finden(View view) {

        int Zaehler = 1;
        KlickSpalte = 1;
        KlickZeile = 1;
        KlickNummer = (int) view.getTag();
        for (int z = 1; z <= AnzZeilen; z = z + 1) {
            for (int s = 1; s <= AnzSpalten; s = s + 1) {
                if (Zaehler == KlickNummer) {
                    KlickSpalte = s;
                    KlickZeile = z;
                    return;
                }
                Zaehler = Zaehler + 1;
            }
        }


    }

    public void Vorlesen_später(String Vorlesetext) {
        if (Sprechen == true) {
            Vorleser.speak(Vorlesetext, TextToSpeech.QUEUE_ADD, null);
        }
    }

    public void Vorlesen_jetzt(String Vorlesetext) {
        if (Sprechen == true) {
            Vorleser.speak(Vorlesetext, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void Einstellungen_Sprache_oeffnen(View view) {
        OWL_anim.start();
    }

    public void Einstellungen_oeffnen(View view) {
        if (Einstellungen_an == false) {
            Einstellungen_an = true;
            Einstellungen_zeigen.setTarget(Einstellungen);
            Einstellungen.setVisibility(View.VISIBLE);
            Einstellungen_zeigen.start();
        } else {
            Einstellungen_an = false;
            Einstellungen_ausblenden.setTarget(Einstellungen);
            Einstellungen_ausblenden.start();
        }
    }

    public void Einstellungen_schliessen(View view) {
        Einstellungen_an = false;
        Einstellungen_ausblenden.setTarget(Einstellungen);
        Einstellungen_ausblenden.start();
    }

    public void Einstellungen_geklickt(int Pos) {

        if (Pos == 0) {
            EinstellungenSprache.setVisibility(View.VISIBLE);
            Einstellungen.setVisibility(View.GONE);
        }
        if (Pos == 1) {
            EinstellungenSpiel.setVisibility(View.VISIBLE);
            Einstellungen.setVisibility(View.GONE);
        }
        if (Pos == 2) {
            Parcelable state = LVEinstellungen.onSaveInstanceState();
            if (Sprechen == true) {
                Sprechen = false;
                EinstellungenBildItem[2] = R.drawable.sprache_aus;
            }
            else {
                Sprechen = true;
                EinstellungenBildItem[2] = R.drawable.sprache_an;
            }
            LVEinstellungen.setAdapter(EinstellungenAdapter);
            LVEinstellungen.onRestoreInstanceState(state);
            //EinstellungenSpiel.setVisibility(View.VISIBLE);
        }
        if (Pos == 3) {
            Pos = 3;
            //EinstellungenTeacher.setVisibility(View.VISIBLE);
        }

        //Parcelable state = LVEinstellungen.onSaveInstanceState();
        //Einstellungen_an = false;
        //EinstellungenItemDurchsichtig[0] = 0.7f;
        //EinstellungenTextKolor[0] = Color.YELLOW;
        //EinstellungenItemDurchsichtig[Pos] = 1;
        //EinstellungenTextKolor[Pos] = Color.WHITE;
        //LVEinstellungen.setAdapter(EinstellungenAdapter);
        //LVEinstellungen.onRestoreInstanceState(state);
    }

    public void Sprache_schliessen(View view) {
        EinstellungenSprache.setVisibility(View.GONE);
        Einstellungen.setVisibility(View.VISIBLE);
    }

    public void Sprache_geklickt(int Pos) {
        Parcelable state = LVSprache.onSaveInstanceState();
        SpracheItemDurchsichtig[SpracheNummer - 1] = 0.7f;
        SpracheTextKolor[SpracheNummer - 1] = Color.BLACK;
        SpracheItemDurchsichtig[Pos] = 1;
        SpracheTextKolor[Pos] = Color.WHITE;
        LVSprache.setAdapter(SpracheAdapter);
        LVSprache.onRestoreInstanceState(state);

        SpracheNummer = Pos + 1;
        Sprache_ändern(SpracheNummer);
    }


    public void Sprache_ändern(int Sprache_NR) {
        if (Sprache_NR == 1) {
            Vorleser.setLanguage(l_en);
            Vorlesen_jetzt("English");
        }
        if (Sprache_NR == 2) {
            Vorleser.setLanguage(l_de);
            Vorlesen_jetzt("Deutsch");
        }
        if (Sprache_NR == 3) {
            Vorleser.setLanguage(l_pl);
            Vorlesen_jetzt("Polski");
        }
        EinstellungenListe_aktualisieren(Sprache_NR);
        LVEinstellungen.setAdapter(EinstellungenAdapter);
    }

    public void EinstellungenListe_aktualisieren(int Sprache_NR) {
        EinstellungenBildItem = new Integer[]{R.drawable.de, R.drawable.level_01, R.drawable.sprache_an, R.drawable.fr};
        EinstellungenTextKolor = new Integer[]{Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE};
        EinstellungenItemDurchsichtig = new float[]{1f, 1f, 1f, 1f};
        if (Sprache_NR == 1) {
            EinstellungenTextItem = new String[]{"Lenguage", "Level", "Voice", "Teacher"};
            TextEinstellungen.setText("SETTINGS");
            EinstellungenBildItem[0] = R.drawable.gb;
        }
        if (Sprache_NR == 2) {
            EinstellungenTextItem = new String[]{"Sprache", "Spiel", "Stimme", "Lehrer"};
            TextEinstellungen.setText("EINSTELLUNGEN");
            EinstellungenBildItem[0] = R.drawable.de;
        }
        if (Sprache_NR == 3) {
            EinstellungenTextItem = new String[]{"Jezyk", "Poziom", "Glos", "Nauczyciel"};
            TextEinstellungen.setText("USTAWIENIA");
            EinstellungenBildItem[0] = R.drawable.pl;
        }
        EinstellungenAdapter = new CustomListAdapter(this, EinstellungenTextItem, EinstellungenBildItem, EinstellungenTextKolor, EinstellungenItemDurchsichtig);
    }

    public void Spiel_oeffnen(View view) {
        if (Spiel_an == false) {
            Spiel_an = true;
            Spiel_zeigen.setTarget(EinstellungenSpiel);
            Spiel_zeigen.start();
        } else {
            Spiel_an = false;
            Spiel_ausblenden.setTarget(EinstellungenSpiel);
            Spiel_ausblenden.start();
        }
    }

    public void Spiel_schliessen(View view) {
        Spiel_an = false;
        Spiel_ausblenden.setTarget(EinstellungenSpiel);
        Spiel_ausblenden.start();
    }

    public void Spiel_geklickt(int Pos) {
        Parcelable state = LVSpiel.onSaveInstanceState();
        //Flagge.setImageResource(SpracheBildItem[Pos]);
        Spiel_an = false;
        //Einstellungen_Sprache.animate().setDuration(300);
        //Einstellungen_Sprache.animate().alphaBy(-1);
        SpielItemDurchsichtig[SpracheNummer - 1] = 0.7f;
        SpielTextKolor[SpracheNummer - 1] = Color.BLACK;
        SpielItemDurchsichtig[Pos] = 1;
        SpielTextKolor[Pos] = Color.WHITE;
        LVSpiel.setAdapter(SpielAdapter);
        LVSpiel.onRestoreInstanceState(state);
        //Sprache.setSelection(Pos);
    }

    /*
        SpracheNummer = SpracheNummer + 1;
        if (SpracheNummer == 4) {SpracheNummer = 1;}
        if (SpracheNummer == 1) {
            Vorleser.setLanguage(l_en);
        }
        if (SpracheNummer == 2) {
            Vorleser.setLanguage(l_de);
        }
        if (SpracheNummer == 3) {
            Vorleser.setLanguage(l_pl);
        }
        //Vorleser.speak(String.valueOf(Schreibbereich.getText()), TextToSpeech.QUEUE_FLUSH, null);
        //Vorleser.speak("klingt wie", TextToSpeech.QUEUE_ADD, null);
        //Vorleser.speak("Ile jest 123 + 54 ?", TextToSpeech.QUEUE_ADD, null);
        //OWL_anim.start();
        }
*/
    // Locale überprüfen
    // Vorleser.addSpeech("bb:", "bertaworld.bertaworldnumbers" , R.raw.pfurz_0);

}
