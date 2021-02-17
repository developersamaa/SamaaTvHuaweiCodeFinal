package com.Samaatv.samaaapp3;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.Samaatv.samaaapp3.fragments.ByCategoryNews;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 12/1/2016.
 */
public class Category_Add extends AppCompatActivity
{

    static
    {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    private final String topics_key = "topics";
    Set<String> news_topics;
    ArrayList<String> pak_ref;
    Set<String> news_topics1;
    SharedPreferences Preferences1;
    private String pak_swtxt, glo_swtxt, enter_swtxt, eco_swtxt, spo_swtxt, edit_swtxt,
            health_swtxt, life_swtxt, social_swtxt, sci_swtxt, weird_swtxt;
    private Switch pak, glo, enter, eco, spo, editors, health, life, social, sci, weird;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_add);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.app_bar_detail);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.toolbar_layoutcolor)));

        news_topics1 = new HashSet<String>();

        Preferences1 = PreferenceManager.getDefaultSharedPreferences(Category_Add.this);
        //news_topics = new HashSet<String>();
        news_topics = Preferences1.getStringSet(topics_key, new HashSet<String>());


        pak = (Switch) findViewById(R.id.pak_switch);
        pak_swtxt = pak.getText().toString();

        glo = (Switch) findViewById(R.id.glo_switch);
        glo_swtxt = glo.getText().toString();

        spo = (Switch) findViewById(R.id.spo_switch);
        spo_swtxt = spo.getText().toString();

        eco = (Switch) findViewById(R.id.eco_switch);
        eco_swtxt = eco.getText().toString();

        enter = (Switch) findViewById(R.id.enter_switch);
        enter_swtxt = enter.getText().toString();

        editors = (Switch) findViewById(R.id.edit_switch);
        edit_swtxt = editors.getText().toString();

        health = (Switch) findViewById(R.id.health_switch);
        health_swtxt = health.getText().toString();

        life = (Switch) findViewById(R.id.life_switch);
        life_swtxt = life.getText().toString();

        social = (Switch) findViewById(R.id.social_switch);
        social_swtxt = social.getText().toString();

        sci = (Switch) findViewById(R.id.sci_switch);
        sci_swtxt = sci.getText().toString();

        weird = (Switch) findViewById(R.id.weird_switch);
        weird_swtxt = weird.getText().toString();

        //Check for current state of news topics
        checkSelectedNewsTopics();
        //ends here


        //attach a listener of Pakistan News to check for changes in state
        pak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                SharedPreferences.Editor editor = Preferences1.edit();

                if (isChecked)
                {
                    // Add the new topic.
                    news_topics1.add("National");
                    news_topics1.addAll(news_topics);
                    editor.putStringSet(topics_key, news_topics1).apply();
                    pak.setText("Remove Topic");
                    Toast.makeText(getApplicationContext(), "Pakistan News Added !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    news_topics1.addAll(news_topics);
                    news_topics1.remove("National");
                    editor.remove("National").apply();
                    editor.putString("National", "").apply();
                    editor.putStringSet(topics_key, news_topics1).apply();
                    pak.setText("Add Topic");
                    Toast.makeText(getApplicationContext(), "Pakistan News Removed !", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //attach a listener of Global News to check for changes in state
        glo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                SharedPreferences.Editor editor = Preferences1.edit();
                if (isChecked)
                {
                    // Add the new topic.
                    news_topics1.add("Global");
                    news_topics1.addAll(news_topics);
                    editor.putStringSet(topics_key, news_topics1);
                    editor.apply();
                    glo.setText("Remove Topic");
                    Toast.makeText(getApplicationContext(), "Global News Added !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    news_topics1.addAll(news_topics);
                    news_topics1.remove("Global");
                    editor.putString("Global", "").apply();
                    editor.putStringSet(topics_key, news_topics1).apply();
                    glo.setText("Add Topic");
                    Toast.makeText(getApplicationContext(), "Global News Removed !", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //attach a listener of Economy News to check for changes in state
        eco.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                SharedPreferences.Editor editor = Preferences1.edit();
                if (isChecked)
                {
                    // Add the new topic.
                    news_topics1.add("Economy");
                    news_topics1.addAll(news_topics);
                    editor.putStringSet(topics_key, news_topics1);
                    editor.apply();
                    eco.setText("Remove Topic");
                    Toast.makeText(getApplicationContext(), "Economy News Added !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Remove global topic.
                    news_topics1.addAll(news_topics);
                    news_topics1.remove("Economy");
                    editor.putStringSet(topics_key, news_topics1);
                    editor.apply();
                    eco.setText("Add Topic");
                    Toast.makeText(getApplicationContext(), "Economy News Removed !", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //attach a listener of Sports News to check for changes in state
        spo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked)
            {
                SharedPreferences.Editor editor = Preferences1.edit();
                if (isChecked)
                {
                    // Add the new topic.
                    news_topics1.add("Sports");
                    news_topics1.addAll(news_topics);
                    editor.putStringSet(topics_key, news_topics1);
                    editor.apply();
                    spo.setText("Remove Topic");
                    Toast.makeText(getApplicationContext(), "Sports News Added !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Remove national topic.
                    news_topics1.addAll(news_topics);
                    news_topics1.remove("Sports");
                    editor.putStringSet(topics_key, news_topics1);
                    editor.apply();
                    spo.setText("Add Topic");
                    Toast.makeText(getApplicationContext(), "Sports News Removed !", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //attach a listener of Entertainment News to check for changes in state
        enter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked)
            {
                SharedPreferences.Editor editor = Preferences1.edit();
                if (isChecked)
                {

                    // Add the new topic.
                    news_topics1.add("Entertainment");
                    news_topics1.addAll(news_topics);
                    editor.putStringSet(topics_key, news_topics1);
                    editor.apply();
                    enter.setText("Remove Topic");
                    Toast.makeText(getApplicationContext(), "Entertainment News Added !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Remove national topic.
                    news_topics1.addAll(news_topics);
                    news_topics1.remove("Entertainment");
                    editor.putStringSet(topics_key, news_topics1);
                    editor.apply();
                    enter.setText("Add Topic");
                    Toast.makeText(getApplicationContext(), "Entertainment News Removed !", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //attach a listener of Sports News to check for changes in state
        editors.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked)
            {
                SharedPreferences.Editor editor = Preferences1.edit();
                if (isChecked)
                {

                    // Add the new topic.
                    news_topics1.add("Editors_Choice");
                    news_topics1.addAll(news_topics);
                    editor.putStringSet(topics_key, news_topics1);
                    editor.apply();
                    editors.setText("Remove Topic");
                    Toast.makeText(getApplicationContext(), "Editors_Choice News Added !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Remove national topic.
                    news_topics1.addAll(news_topics);
                    news_topics1.remove("Editors_Choice");
                    editor.putStringSet(topics_key, news_topics1);
                    editor.apply();
                    editors.setText("Add Topic");
                    Toast.makeText(getApplicationContext(), "Editors_Choice News Removed !", Toast.LENGTH_SHORT).show();
                }
            }
        });


        health.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                SharedPreferences.Editor editor = Preferences1.edit();
                if (isChecked)
                {

                    // Add the new topic.
                    news_topics1.add("Health");
                    news_topics1.addAll(news_topics);
                    editor.putStringSet(topics_key, news_topics1);
                    editor.apply();
                    health.setText("Remove Topic");
                    Toast.makeText(getApplicationContext(), "Health News Added !", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    // Remove national topic.
                    news_topics1.addAll(news_topics);
                    news_topics1.remove("Health");
                    editor.putStringSet(topics_key, news_topics1);
                    editor.apply();
                    health.setText("Add Topic");
                    Toast.makeText(getApplicationContext(), "Health News Removed !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        life.setOnCheckedChangeListener(new LifeSwitchListner());

        social.setOnCheckedChangeListener(new SocialSwitchListner());

        sci.setOnCheckedChangeListener(new SciTechSwitchListner());

        weird.setOnCheckedChangeListener(new WeirdSwitchListner());
    }

    private void checkSelectedNewsTopics()
    {
        if (news_topics.contains("National"))
        {
            pak.setText("Remove Topic");
            pak.setChecked(true);
        }
        else
        {
            pak.setText("Add Topic");
            pak.setChecked(false);
        }

        if (news_topics.contains("Global"))
        {
            glo.setText("Remove Topic");
            glo.setChecked(true);
        }
        else
        {
            glo.setText("Add Topic");
            glo.setChecked(false);
        }

        if (news_topics.contains("Economy"))
        {
            eco.setText("Remove Topic");
            eco.setChecked(true);
        }
        else
        {
            eco.setText("Add Topic");
            eco.setChecked(false);
        }

        if (news_topics.contains("Sports"))
        {
            spo.setText("Remove Topic");
            spo.setChecked(true);
        }
        else
        {
            spo.setText("Add Topic");
            spo.setChecked(false);
        }

        if (news_topics.contains("Entertainment"))
        {
            enter.setText("Remove Topic");
            enter.setChecked(true);
        }
        else
        {
            enter.setText("Add Topic");
            enter.setChecked(false);
        }

        if (news_topics.contains("Editors_Choice"))
        {
            editors.setText("Remove Topic");
            editors.setChecked(true);
        }
        else
        {
            editors.setText("Add Topic");
            editors.setChecked(false);
        }

        if (news_topics.contains("Health"))
        {
            health.setText("Remove Topic");
            health.setChecked(true);
        }
        else
        {
            health.setText("Add Topic");
            health.setChecked(false);
        }

        if (news_topics.contains("lifestyle"))
        {
            life.setText("Remove Topic");
            life.setChecked(true);
        }
        else
        {
            life.setText("Add Topic");
            life.setChecked(false);
        }

        if (news_topics.contains("social"))
        {
            social.setText("Remove Topic");
            social.setChecked(true);
        }
        else
        {
            social.setText("Add Topic");
            social.setChecked(false);
        }

        if (news_topics.contains("scitech"))
        {
            sci.setText("Remove Topic");
            sci.setChecked(true);
        }
        else
        {
            sci.setText("Add Topic");
            sci.setChecked(false);
        }

        if (news_topics.contains("weird"))
        {
            weird.setText("Remove Topic");
            weird.setChecked(true);
        }
        else
        {
            weird.setText("Add Topic");
            weird.setChecked(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        ByCategoryNews.AllowRefresh = true;
        if (news_topics.size() == 0)
        {
            news_topics1.clear();
        }
        finish();
    }

    private class HealthSwitchListner implements CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            SharedPreferences.Editor editor = Preferences1.edit();

            if (isChecked)
            {
                // Add the new topic.
                news_topics1.add("Health");
                news_topics1.addAll(news_topics);
                editor.putStringSet(topics_key, news_topics1);
                editor.apply();
                editors.setText("Remove Topic");
                Toast.makeText(getApplicationContext(), "Health News Added !", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Remove national topic.
                news_topics1.addAll(news_topics);
                news_topics1.remove("Health");
                editor.putStringSet(topics_key, news_topics1);
                editor.apply();
                editors.setText("Add Topic");
                Toast.makeText(getApplicationContext(), "Health News Removed !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class LifeSwitchListner implements CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            SharedPreferences.Editor editor = Preferences1.edit();

            if (isChecked)
            {
                // Add the new topic.
                news_topics1.add("lifestyle");
                news_topics1.addAll(news_topics);
                editor.putStringSet(topics_key, news_topics1);
                editor.apply();
                life.setText("Remove Topic");
                Toast.makeText(getApplicationContext(), "Life Stlye News Added !", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Remove national topic.
                news_topics1.addAll(news_topics);
                news_topics1.remove("lifestyle");
                editor.putStringSet(topics_key, news_topics1);
                editor.apply();
                life.setText("Add Topic");
                Toast.makeText(getApplicationContext(), "Life Stlye News Removed !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SocialSwitchListner implements CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            SharedPreferences.Editor editor = Preferences1.edit();

            if (isChecked)
            {
                // Add the new topic.
                news_topics1.add("social");
                news_topics1.addAll(news_topics);
                editor.putStringSet(topics_key, news_topics1);
                editor.apply();
                social.setText("Remove Topic");
                Toast.makeText(getApplicationContext(), "Social Buzz News Added !", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Remove national topic.
                news_topics1.addAll(news_topics);
                news_topics1.remove("social");
                editor.putStringSet(topics_key, news_topics1);
                editor.apply();
                social.setText("Add Topic");
                Toast.makeText(getApplicationContext(), "Social Buzz News Removed !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SciTechSwitchListner implements CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {

            SharedPreferences.Editor editor = Preferences1.edit();
            if (isChecked)
            {
                // Add the new topic.
                news_topics1.add("scitech");
                news_topics1.addAll(news_topics);
                editor.putStringSet(topics_key, news_topics1);
                editor.apply();
                sci.setText("Remove Topic");
                Toast.makeText(getApplicationContext(), "Sci Tech News Added !", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Remove national topic.
                news_topics1.addAll(news_topics);
                news_topics1.remove("scitech");
                editor.putStringSet(topics_key, news_topics1);
                editor.apply();
                sci.setText("Add Topic");
                Toast.makeText(getApplicationContext(), "Sci Tech News Removed !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class WeirdSwitchListner implements CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {

            SharedPreferences.Editor editor = Preferences1.edit();
            if (isChecked)
            {
                // Add the new topic.
                news_topics1.add("weird");
                news_topics1.addAll(news_topics);
                editor.putStringSet(topics_key, news_topics1);
                editor.apply();
                weird.setText("Remove Topic");
                Toast.makeText(getApplicationContext(), "Weird News Added !", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Remove national topic.
                news_topics1.addAll(news_topics);
                news_topics1.remove("weird");
                editor.putStringSet(topics_key, news_topics1);
                editor.apply();
                weird.setText("Add Topic");
                Toast.makeText(getApplicationContext(), "Weird News Removed !", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //    @Override
//    protected void onPause() {
//        super.onPause();
//        checkSelectedNewsTopics();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        checkSelectedNewsTopics();
//    }
}


