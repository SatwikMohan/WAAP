package com.gigawattstechnology.waapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class WorkSpace extends AppCompatActivity implements onItemClickInteface {

    String[] toolList={"Button","ImageView","EditText","TextView"};
    RelativeLayout relativeLayout;
    RecyclerView recyclerView;
    float X,Y;
    Button showtoolbar,hidetoolbar;
    Toolbar toolbar;
    int red=0,green=0,blue=0;
    int idTextView=0,idButton=0,idEditText=0,idImage=0;
    Uri selectedImageUri;
    giveImage giveImage = new giveImage();
    FloatingActionButton moreOptions,drawOptions;
    ArrayList<ToolButton> ButtonPresent=new ArrayList<>();
    ArrayList<ToolImageView> ImageViewPresent=new ArrayList<>();
    ArrayList<ToolTextView> TextViewPresent=new ArrayList<>();
    ArrayList<ToolEditText> EditTextPresent=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        moreOptions=findViewById(R.id.moreOptions);
        drawOptions=findViewById(R.id.drawOptions);
        showtoolbar=findViewById(R.id.ShowToolBar);
        hidetoolbar=findViewById(R.id.HideToolBar);
        toolbar=findViewById(R.id.ToolMenu);
        relativeLayout=findViewById(R.id.relativeLayout);
        recyclerView=findViewById(R.id.toolSlide);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new ToolRecycleAdapter(this,toolList,WorkSpace.this));
        showtoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar.setVisibility(View.VISIBLE);
                showtoolbar.setVisibility(View.GONE);
            }
        });
        hidetoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar.setVisibility(View.GONE);
                showtoolbar.setVisibility(View.VISIBLE);
            }
        });

        moreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(WorkSpace.this,view);
                popupMenu.getMenuInflater().inflate(R.menu.moreoptions_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle().equals("Set Background Color")){
                            LayoutInflater layoutInflater = LayoutInflater.from(WorkSpace.this);
                            final View BackgroundView = layoutInflater.inflate(R.layout.layout_setbackgroundcolor, null);
                            SeekBar seekRed=BackgroundView.findViewById(R.id.RedColorSeekBar);
                            seekRed.setMax(255);
                            SeekBar seekGreen=BackgroundView.findViewById(R.id.GreenColorSeekBar);
                            seekGreen.setMax(255);
                            SeekBar seekBlue=BackgroundView.findViewById(R.id.BlueColorSeekBar);
                            seekBlue.setMax(255);
                            LinearLayout ColorGuide=BackgroundView.findViewById(R.id.ColorGuide);
                            seekRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                @Override
                                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                    red=i;
                                    ColorGuide.setBackgroundColor(Color.rgb(red,green,blue));
                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            });
                            seekGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                @Override
                                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                    green=i;
                                    ColorGuide.setBackgroundColor(Color.rgb(red,green,blue));
                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            });
                            seekBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                @Override
                                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                    blue=i;
                                    ColorGuide.setBackgroundColor(Color.rgb(red,green,blue));
                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            });
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(WorkSpace.this);
                            alertDialog.setTitle("Annotate Background").setView(BackgroundView).setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    relativeLayout.setBackgroundColor(Color.rgb(red,green,blue));
                                }
                            }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialog.show();
                        }
                        if (menuItem.getTitle().equals("Set Background Image")){

                            LayoutInflater layoutInflater = LayoutInflater.from(WorkSpace.this);
                            final View BackgroundView = layoutInflater.inflate(R.layout.layout_setbackgroundimage, null);
                            RadioButton bg1=BackgroundView.findViewById(R.id.imageBackground1);
                            RadioButton bg2=BackgroundView.findViewById(R.id.imageBackground2);
                            RadioButton bg3=BackgroundView.findViewById(R.id.imageBackground3);
                            RadioButton bg4=BackgroundView.findViewById(R.id.imageBackground4);
                            RadioButton bg5=BackgroundView.findViewById(R.id.imageBackground5);
                            RadioButton bg6=BackgroundView.findViewById(R.id.imageBackground6);
                            RadioButton bg7=BackgroundView.findViewById(R.id.imageBackground7);
                            AlertDialog.Builder alertDialog=new AlertDialog.Builder(WorkSpace.this);
                            alertDialog.setTitle("Annotate Background").setView(BackgroundView).setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    if (bg1.isChecked()){
                                        relativeLayout.setBackgroundResource(R.drawable.bg1);
                                    }
                                    if (bg2.isChecked()){
                                        relativeLayout.setBackgroundResource(R.drawable.bg2);
                                    }
                                    if (bg3.isChecked()){
                                        relativeLayout.setBackgroundResource(R.drawable.bg3);
                                    }
                                    if (bg4.isChecked()){
                                        relativeLayout.setBackgroundResource(R.drawable.bg4);
                                    }
                                    if (bg5.isChecked()){
                                        relativeLayout.setBackgroundResource(R.drawable.bg5);
                                    }
                                    if (bg6.isChecked()){
                                        relativeLayout.setBackgroundResource(R.drawable.bg6);
                                    }
                                    if (bg7.isChecked()){
                                        relativeLayout.setBackgroundResource(R.drawable.bg7);
                                    }

                                }
                            }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialog.show();

                        }
                        if (menuItem.getTitle().equals("Draw On Screen")){

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


    }

    private void onToolTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                X=view.getX()-motionEvent.getRawX();
                Y=view.getY()-motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                view.animate().x(motionEvent.getRawX()+X).y(motionEvent.getRawY()+Y).setDuration(0).start();
        }
    }

    @Override
    public void onItemClick(int position) {

        if(toolList[position].equals("ImageView")){
            idImage++;
            ImageView imageView=new ImageView(this);
            imageView.setImageResource(R.drawable.ic_baseline_photo_24);
            imageView.setId(idImage);
            ImageViewPresent.add(new ToolImageView(imageView,idImage));
            addImageView(imageView,400,400);
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu popupMenu=new PopupMenu(WorkSpace.this,view);
                    popupMenu.getMenuInflater().inflate(R.menu.imageview_popup, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            if (menuItem.getTitle().equals("Show ID")){
                                Toast.makeText(WorkSpace.this, ""+imageView.getId(), Toast.LENGTH_SHORT).show();
                            }

                            if(menuItem.getTitle().equals("Set Image")){
                                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                intent.setType("image/*");
                                startActivityForResult(intent, 1);
                                giveImage.setImageView(imageView);
                            }
                            if(menuItem.getTitle().equals("Drag On")){
                                imageView.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View view, MotionEvent motionEvent) {
                                        onToolTouch(view, motionEvent);
                                        return false;
                                    }
                                });
                            }
                            if(menuItem.getTitle().equals("Drag Off")){
                                imageView.setOnTouchListener(null);
                            }
                            if(menuItem.getTitle().equals("Remove")){
                                ImageViewPresent.remove(new ToolImageView(imageView,imageView.getId()));
                                imageView.setVisibility(View.GONE);
                                Toast.makeText(WorkSpace.this, "ImageView Removed ", Toast.LENGTH_LONG).show();
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                    return false;
                }
            });

        }


        if(toolList[position].equals("Button")){

            idButton++;
            Button button=new Button(this);
            button.setBackgroundColor(getResources().getColor(android.R.color.system_neutral1_700));
            button.setText("Process"+idButton);
            button.setTextColor(getResources().getColor(R.color.white));
            button.setId(idButton);
            ButtonPresent.add(new  ToolButton(button,idButton));
            addButton(button);
            PopupMenu popupMenu=new PopupMenu(WorkSpace.this,button);
            popupMenu.getMenuInflater().inflate(R.menu.button_popup, popupMenu.getMenu());
            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    MenuItem DragOff=popupMenu.getMenu().findItem(R.id.buttonDragOff);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            if (menuItem.getTitle().equals("Show ID")){
                                Toast.makeText(WorkSpace.this, ""+button.getId(), Toast.LENGTH_SHORT).show();
                            }

                            if(menuItem.getTitle().equals("Drag On")){

                                DragOff.setTitle("Drag Off");
                                button.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View view, MotionEvent motionEvent) {
                                        onToolTouch(view,motionEvent);
                                        return false;
                                    }
                                });

                            }

                            if(menuItem.getTitle().equals("Drag Off")){

                                button.setOnTouchListener(null);
                                DragOff.setTitle("Set On Click Listener");

                            }

                            if (menuItem.getTitle().equals("Set On Click Listener")){



                            }

                            if (menuItem.getTitle().equals("Set Button Color")){

                                LayoutInflater layoutInflater = LayoutInflater.from(WorkSpace.this);
                                final View BackgroundView = layoutInflater.inflate(R.layout.layout_setbackgroundcolor, null);
                                SeekBar seekRed=BackgroundView.findViewById(R.id.RedColorSeekBar);
                                seekRed.setMax(255);
                                SeekBar seekGreen=BackgroundView.findViewById(R.id.GreenColorSeekBar);
                                seekGreen.setMax(255);
                                SeekBar seekBlue=BackgroundView.findViewById(R.id.BlueColorSeekBar);
                                seekBlue.setMax(255);
                                LinearLayout ColorGuide=BackgroundView.findViewById(R.id.ColorGuide);
                                seekRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                        red=i;
                                        ColorGuide.setBackgroundColor(Color.rgb(red,green,blue));
                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {

                                    }
                                });
                                seekGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                        green=i;
                                        ColorGuide.setBackgroundColor(Color.rgb(red,green,blue));
                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {

                                    }
                                });
                                seekBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                        blue=i;
                                        ColorGuide.setBackgroundColor(Color.rgb(red,green,blue));
                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {

                                    }
                                });
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(WorkSpace.this);
                                alertDialog.setTitle("Annotate Button Background").setView(BackgroundView).setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        button.setBackgroundColor(Color.rgb(red,green,blue));
                                    }
                                }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                alertDialog.show();

                            }
                            if(menuItem.getTitle().equals("Set Text")){
                                LayoutInflater layoutInflater = LayoutInflater.from(WorkSpace.this);
                                final View EntryView = layoutInflater.inflate(R.layout.layout_button, null);
                                EditText setText=EntryView.findViewById(R.id.editTextButtonSetText);
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(WorkSpace.this);
                                alertDialog.setTitle("Annotate Button").setView(EntryView).setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        button.setText(setText.getText().toString());
                                    }
                                }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                                alertDialog.show();

                            }
                            if (menuItem.getTitle().equals("Remove")){

                                ButtonPresent.remove(new ToolButton(button,button.getId()));
                                button.setVisibility(View.GONE);
                                Toast.makeText(WorkSpace.this, "Button Removed ", Toast.LENGTH_LONG).show();
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                    return false;
                }
            });

        }
        if(toolList[position].equals("EditText")){

            idEditText++;
            EditText editText=new EditText(this);
            editText.setHint("Start Writing In Here "+idEditText);
            editText.setHintTextColor(getResources().getColor(R.color.black));
            editText.setTextColor(getResources().getColor(R.color.black));
            editText.setId(idEditText);
            EditTextPresent.add(new ToolEditText(editText,idEditText));
            addEditText(editText);
            PopupMenu popupMenu=new PopupMenu(WorkSpace.this,editText);
            popupMenu.getMenuInflater().inflate(R.menu.edittext_popup, popupMenu.getMenu());
           // MenuItem DragOn=popupMenu.getMenu().findItem(R.id.EditTextDragOn);
            editText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            if (menuItem.getTitle().equals("Set Input Type")){
                                LayoutInflater layoutInflater = LayoutInflater.from(WorkSpace.this);
                                final View InputView = layoutInflater.inflate(R.layout.layout_edittextinputtype, null);
                                RadioButton inputTypePassword=InputView.findViewById(R.id.inputTypePassword);
                                RadioButton inputTypeNumeric=InputView.findViewById(R.id.inputTypeNumeric);
                                RadioButton inputTypeText=InputView.findViewById(R.id.inputTypeText);
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(WorkSpace.this);
                                alertDialog.setTitle("Annotate EditText Input Type").setView(InputView).setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (inputTypePassword.isChecked()){
                                            editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
                                        }

                                        if (inputTypeNumeric.isChecked()){
                                            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        }

                                        if (inputTypeText.isChecked()){
                                            editText.setInputType(InputType.TYPE_CLASS_TEXT);
                                        }

                                    }
                                }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                alertDialog.show();
                            }

                            if (menuItem.getTitle().equals("Show ID")){
                                Toast.makeText(WorkSpace.this, ""+editText.getId(), Toast.LENGTH_SHORT).show();
                            }

                            if (menuItem.getTitle().equals("Set Hint Color")){

                                LayoutInflater layoutInflater = LayoutInflater.from(WorkSpace.this);
                                final View BackgroundView = layoutInflater.inflate(R.layout.layout_setbackgroundcolor, null);
                                SeekBar seekRed=BackgroundView.findViewById(R.id.RedColorSeekBar);
                                seekRed.setMax(255);
                                SeekBar seekGreen=BackgroundView.findViewById(R.id.GreenColorSeekBar);
                                seekGreen.setMax(255);
                                SeekBar seekBlue=BackgroundView.findViewById(R.id.BlueColorSeekBar);
                                seekBlue.setMax(255);
                                LinearLayout ColorGuide=BackgroundView.findViewById(R.id.ColorGuide);
                                seekRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                        red=i;
                                        ColorGuide.setBackgroundColor(Color.rgb(red,green,blue));
                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {

                                    }
                                });
                                seekGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                        green=i;
                                        ColorGuide.setBackgroundColor(Color.rgb(red,green,blue));
                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {

                                    }
                                });
                                seekBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                        blue=i;
                                        ColorGuide.setBackgroundColor(Color.rgb(red,green,blue));
                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {

                                    }
                                });
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(WorkSpace.this);
                                alertDialog.setTitle("Annotate EditText Hint Color").setView(BackgroundView).setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        editText.setHintTextColor(Color.rgb(red,green,blue));
                                    }
                                }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                alertDialog.show();

                            }
                            if (menuItem.getTitle().equals("Set Hint")){
                                LayoutInflater layoutInflater = LayoutInflater.from(WorkSpace.this);
                                final View EntryView = layoutInflater.inflate(R.layout.layout_edittext, null);
                                EditText setHint = EntryView.findViewById(R.id.editTextSetHint);
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(WorkSpace.this);
                                alertDialog.setTitle("Annotate EditText").setView(EntryView).setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        editText.setHint(setHint.getText());

                                    }
                                }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                alertDialog.show();
                            }
                            if (menuItem.getTitle().equals("Set Text Color")){
                                LayoutInflater layoutInflater = LayoutInflater.from(WorkSpace.this);
                                final View BackgroundView = layoutInflater.inflate(R.layout.layout_setbackgroundcolor, null);
                                SeekBar seekRed=BackgroundView.findViewById(R.id.RedColorSeekBar);
                                seekRed.setMax(255);
                                SeekBar seekGreen=BackgroundView.findViewById(R.id.GreenColorSeekBar);
                                seekGreen.setMax(255);
                                SeekBar seekBlue=BackgroundView.findViewById(R.id.BlueColorSeekBar);
                                seekBlue.setMax(255);
                                LinearLayout ColorGuide=BackgroundView.findViewById(R.id.ColorGuide);
                                seekRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                        red=i;
                                        ColorGuide.setBackgroundColor(Color.rgb(red,green,blue));
                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {

                                    }
                                });
                                seekGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                        green=i;
                                        ColorGuide.setBackgroundColor(Color.rgb(red,green,blue));
                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {

                                    }
                                });
                                seekBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                        blue=i;
                                        ColorGuide.setBackgroundColor(Color.rgb(red,green,blue));
                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {

                                    }
                                });
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(WorkSpace.this);
                                alertDialog.setTitle("Annotate EditText Text Color").setView(BackgroundView).setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        editText.setTextColor(Color.rgb(red,green,blue));
                                    }
                                }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                alertDialog.show();
                            }
                            if (menuItem.getTitle().equals("Remove")){
                                EditTextPresent.remove(new ToolEditText(editText,editText.getId()));
                                editText.setVisibility(View.GONE);
                                Toast.makeText(WorkSpace.this, "EditText Removed ", Toast.LENGTH_SHORT).show();
                            }
                            if (menuItem.getTitle().equals("Drag On")){

                                editText.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View view, MotionEvent motionEvent) {
                                        onToolTouch(view, motionEvent);
                                        return false;
                                    }
                                });
                                //DragOn.setTitle("Drag Off");

                            }
                            if (menuItem.getTitle().equals("Drag Off")){

                               // DragOn.setTitle("Drag On");
                                editText.setOnTouchListener(null);

                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                    return false;
                }
            });

        }
        if(toolList[position].equals("TextView")) {

            idTextView++;
            TextView textView = new TextView(this);
            textView.setText("TextView"+idTextView);
            textView.setId(idTextView);
            textView.setTextColor(getResources().getColor(R.color.black));
            TextViewPresent.add(new ToolTextView(textView,idTextView));
            addTextView(textView);
            textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    onToolTouch(view, motionEvent);
                    //Toast.makeText(WorkSpace.this, ""+textView.getId(), Toast.LENGTH_SHORT).show();
                    return false;
                    }
                });

                textView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        LayoutInflater layoutInflater = LayoutInflater.from(WorkSpace.this);
                        final View EntryView = layoutInflater.inflate(R.layout.layout_textview, null);
                        EditText setText = EntryView.findViewById(R.id.editTextSetText);
                        Button remove=EntryView.findViewById(R.id.textRemoveButton);
                        remove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                TextViewPresent.remove(new ToolTextView(textView,textView.getId()));
                                textView.setVisibility(View.GONE);
                                Toast.makeText(WorkSpace.this, "TextView Removed, Click Anywhere", Toast.LENGTH_LONG).show();
                            }
                        });
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(WorkSpace.this);
                        alertDialog.setTitle("Annotate TextView").setView(EntryView).setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                textView.setText(setText.getText());

                            }
                        }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alertDialog.show();
                        return true;
                    }
                });

        }
    }

    private int getRandomColor() {
        Random random=new Random();
        int color= Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256));
        return color;
    }

    private void addImageView(ImageView imageView, int width, int height) {
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(width,height);
        layoutParams.setMargins(0,10,0,10);
        imageView.setLayoutParams(layoutParams);
        relativeLayout.addView(imageView);
    }
    private void addButton(Button button) {
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,10,0,10);
        button.setLayoutParams(layoutParams);
        relativeLayout.addView(button);
    }
    private void addEditText(EditText editText) {
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,10,0,10);
        editText.setLayoutParams(layoutParams);
        relativeLayout.addView(editText);
    }
    private void addTextView(TextView textView) {
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,10,0,10);
        textView.setLayoutParams(layoutParams);
        relativeLayout.addView(textView);
    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null) {
            selectedImageUri = data.getData();
            final String path = getPathFromURI(selectedImageUri);
            if (path != null) {
                File f = new File(path);
                selectedImageUri = Uri.fromFile(f);
            }
            giveImage.getImageView().setImageURI(selectedImageUri);
        }
    }
}