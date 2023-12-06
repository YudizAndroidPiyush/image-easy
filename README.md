# ImageEasy (WhatsApp Style Image and Video Picker)

ImageEasy is a WhatsApp image picker replica. with this you can integrate a image picker just like
WhatsApp.

## Usage

set configuration as

```kotlin
    val options = Options().apply {
    ratio = Ratio.RATIO_AUTO                                    //Image/video capture ratio
    count =
        1                                                   //Number of images to restrict selection count
    spanCount = 4                                               //Number for columns in grid
    path =
        "ImageEasy/Camera"                                         //Custom Path For media Storage
    isFrontFacing = false                                       //Front Facing camera on start
    videoDurationLimitInSeconds = 10                            //Duration for video recording
    mode =
        Mode.All                                             //Option to select only pictures or videos or both
    flash = Flash.Auto                                          //Option to select flash type
    preSelectedUrls = ArrayList<Uri>()                          //Pre selected Image Urls
}

```

Ratio can be

```kotlin
  RATIO_4_3, RATIO_16_9, RATIO_AUTO
```

Mode to to select the media type can be as

```kotlin
  All, Picture, Video
```

Then pass this config to the ImageEasy fragment either via

```kotlin
     addimageEasyToActivity(R.id.container, options) {
    when (it.status) {
        imageEasyEventCallback.Status.SUCCESS -> //use results as it.data
            imageEasyEventCallback.Status.BACK_PRESSED
        -> // back pressed called
    }
}
```

or plain fragment can be retrieved via

```kotlin
private val imageEasyFragment = imageEasyFragment(options)
```

The results can be retrieved via the constructor callback from the fragment

```kotlin
    imageEasyFragment(options) {
    when (it.status) {
        imageEasyEventCallback.Status.SUCCESS -> //use results as it.data
            imageEasyEventCallback.Status.BACK_PRESSED
        -> // back pressed called
    }
}
```

Or can be retrieved by anywhere in the Application from the state flow eventbus

```kotlin
    imageEasyBus.results {
    when (it.status) {
        imageEasyEventCallback.Status.SUCCESS ->  //use results as it.data
            imageEasyEventCallback.Status.BACK_PRESSED
        -> // back pressed called
    }
}
```

For detailed usage kindly refer to the below samples

## Customise

### Theme

include these items in colors.xml with custom color codes

```xml

<resources>
    <color name="video_counter_color_imageEasy">#E53935</color>
    <color name="primary_color_imageEasy">#1A43AC</color>
    <color name="primary_light_color_imageEasy">#801A43AC</color>
    <color name="surface_color_imageEasy">#ffffff</color>
    <color name="text_color_imageEasy">#807f7f</color>
</resources>
```
Gradle:

include in app level build.gradle

 ```groovy
 repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
}
 ```

```groovy
 implementation 'com.github.YudizAndroidPiyush:image-easy:1.0.1'
```

or Maven:

or ivy:

```xml

<dependency name='imageEasyimagepicker' org='com.yudiz.easy' rev='1.0.1'>
    <artifact name='ImageEasy' ext='pom'></artifact>
</dependency>
```
