# AnimatedDismissableActivity
A simple cool dismissal animation for activities

![Dismissable Library Animation](https://media.giphy.com/media/l378eDbwIlmiofb0c/giphy.gif)

## Installation:
1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
2. Add the dependency
```
dependencies {
  compile 'com.github.OpenCraft:AnimatedDismissableActivity:-SNAPSHOT'
}
```

## Usage:
Start your own dismissable activity with:
```
Intent intent = new Intent(this, AnimatedCardDismissableSampleActivity.class);
startActivity(intent);
overridePendingTransition(R.anim.animated_dismissable_card_slide_up_anim, R.anim.animated_dismissable_card_stay_anim);
```

Your custom dismissable activity, should looks like this:
```
public class AnimatedCardDismissableSampleActivity extends Activity { 
  private AnimatedDismissableCard animatedCard; 

  @Override protected void onCreate(Bundle savedInstanceState) { 
    super.onCreate(savedInstanceState); 
    MyNewAnimatedCardBinding binding = DataBindingUtil.setContentView(this, R.layout.yourActivity); 
    animatedCard = new AnimatedDismissableCard(this, binding.yourViewGroupLayout); 
  } 

  public void onDismissCardBtnClick(View view) { animatedCard.dismiss(); } 
}
```

**Customizing:** <br />
Create your own res/values/integers.xml to override default configuration:
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <integer name="animated_dismissable_activity_animations_duration">1000</integer>
    <integer name="animated_dismissable_activity_dismiss_with_touch_percentage">30</integer>
</resources>
```
