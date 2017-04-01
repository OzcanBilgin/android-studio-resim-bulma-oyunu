package com.example.user.resimbulma2;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import java.util.Random;

public class menu extends AppCompatActivity implements View.OnClickListener {
    private int numberOfElements; // topalam satir sutun sayisini tutar

    private MemoryButton[] buttons; //memory class button tutturma

    private int[] buttonGraphicLocations; // toplam buton resimlerini tutturulacak
    private int[] buttonGraphics;  // 8 adet resmi tutturcaz int olarak

    private MemoryButton selectedButton1;  // memory buton için 1 tane seçim yatırcak
    private MemoryButton selectedButton2;  // memory buton için 1 tane seçim yatırcak

    private boolean isBusy = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        GridLayout gridLayout = (GridLayout)findViewById(R.id.grid_layout_4X4); //GridL getirdi

        int numColumns = gridLayout.getColumnCount();  //sutun sayısı getirdi tutturdu
        int numRows = gridLayout.getRowCount();       // satir sayisini  getirdi

        numberOfElements = numColumns * numRows ;  // satir sütün satisini çarpıp tutturdu

        buttons = new MemoryButton[numberOfElements];  //buttons  atandi satir sutun sayısının çarpimini verdik

        buttonGraphics = new int[numberOfElements / 2]; // resim sayisi kadar atadik 8 adet resim
         //bG atadik resimleri
        buttonGraphics[0] = R.drawable.card1;
        buttonGraphics[1] = R.drawable.card2;
        buttonGraphics[2] = R.drawable.card3;
        buttonGraphics[3] = R.drawable.card4;
        buttonGraphics[4] = R.drawable.card5;
        buttonGraphics[5] = R.drawable.card6;
        buttonGraphics[6] = R.drawable.card7;
        buttonGraphics[7] = R.drawable.card8;


        //BGL toplam satır sutun carpımını yerlestırdık 16 tane
        buttonGraphicLocations = new int[numberOfElements];

        // karistirma işlemi yaptik
        shuffleButtonGraphics();
        // satir sayisi kadar döndürür
        for(int r = 0; r <numRows; r++ ){
          // sütün sayisi kadar döndürür
            for(int c = 0; c < numColumns; c++ ){
                MemoryButton tempButton = new MemoryButton(this, r, c,
                        buttonGraphics[buttonGraphicLocations[r * numColumns + c]]);// memory button çağirir resim atamasi yapar ön yüz atamasi
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                buttons[r * numColumns + c] = tempButton;
                gridLayout.addView(tempButton); // ekleme biter
            }
        }


    }

    protected void shuffleButtonGraphics(){
        Random rnd = new Random();
        // BGL dizisine 16 kadar değer atama
        for(int i=0; i< numberOfElements; i++){

            buttonGraphicLocations[i] = i % (numberOfElements / 2); // mod alır ve 0,1,2....7,0,1...7 kadar atannir BGL[0]..BGL[15] kadar
        }
        // BGL dizisine atılan değerleri kariştirma
        for(int i=0; i< numberOfElements; i++){
            int temp = buttonGraphicLocations[i];
            int swapindex = rnd.nextInt(numberOfElements);
            buttonGraphicLocations[i] = buttonGraphicLocations[swapindex];
            buttonGraphicLocations[swapindex] = temp;
        }

    }

    @Override
    public void onClick(View v) {
        // meskul mu diye kontrol eder
     if(isBusy)
            return;

        MemoryButton button = (MemoryButton) v; //MB buton tutturduk

        if(button.isMatched()) // buton eşleşmesi yaptık
            return;

        if(selectedButton1 == null){ // eğer boşsa
            selectedButton1 = button; // selectbutton attık
            selectedButton1.flip();  //çevirdik
            return;
        }

        if(selectedButton1.getId() == button.getId())
            return;

        if(selectedButton1.getFrontDrawableId() == button.getFrontDrawableId()){ //getFrontDrawableId çağırır  ve ön yüzler eşlesti mi kontrol et id bak
            button.flip(); // buton çevir

            button.setMatched(true); // eşletirme sağlandi
            selectedButton1.setMatched(true); //eşleştirme doğru

            selectedButton1.setEnabled(false); // döndürüldü etkin değil false olarak
            button.setEnabled(false);

            selectedButton1 = null; // boşaltıldı
            return;
        }
        else{
            selectedButton2 = button; // butona atandi
            selectedButton2.flip(); // döndürüldü
            isBusy = true; // meşul oldu

            final Handler handler = new Handler();// belirli aralıklarl çalişmasını istiyorsak bir kodun bu fonksiyon kullanılır
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectedButton1.flip(); // seçili olan görünsün
                    selectedButton2.flip();
                    selectedButton1 = null; // boşaltılsın
                    selectedButton2 = null;
                    isBusy = false; // meşkul ol

                }
            }, 500); // 500 ms dönsün geri
        }


    }

}
