![Preview image](media/header.gif)

# ImageEasy (WhatsApp Style Image and Video Picker)

ImageEasy is a WhatsApp image picker replica. with this you can integrate a image picker just like
WhatsApp.

[![](https://img.shields.io/badge/Android%20Arsenal-imageEasyImagePicker-blue.svg?style=flat-square)](https://android-arsenal.com/details/1/6935)
[![](https://img.shields.io/badge/Medium-ImageEasy-black.svg?style=flat-square)](https://medium.com/@fxn769/ImageEasy-media-picker-android-library-1ec3c5e5f91a)
[![](https://img.shields.io/badge/API-16%2B-orange.svg?style=flat-square)](https://android-arsenal.com/api?level=16)
[![ImageEasy Image Picker](https://www.appbrain.com/stats/libraries/shield/ImageEasy-image-picker.svg)](https://www.appbrain.com/stats/libraries/details/ImageEasy-image-picker/ImageEasy-image-picker)

## Demo

![](media/two.gif)

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

## Thanks to

- [Glide]
- [FastScroll]
- [Header-decor]
- [CameraX]
- [Coroutines]

## Backers

Become a backer and help us sustain our activities! 🙏🙏
<a href="https://opencollective.com/imageEasyimagepicker#backers" target="_blank"><img src="https://opencollective.com/imageEasyimagepicker/backers.svg?width=890"></a>

## Download

[Download](https://search.maven.org/artifact/com.yudiz.ease/imageEasyimagepicker) or grab via
Gradle:

include in app level build.gradle

 ```groovy
 repositories {
    mavenCentral()
}
 ```

```groovy
 implementation 'com.yudiz.easy:imageEasyimagepicker:1.6.3'
```

or Maven:

```xml

<dependency>
    <groupId>com.yudiz.easy</groupId>
    <artifactId>imageEasyimagepicker</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
</dependency>
```

or ivy:

```xml

<dependency name='imageEasyimagepicker' org='com.yudiz.easy' rev='1.0.0'>
    <artifact name='ImageEasy' ext='pom'></artifact>
</dependency>
```

## Author & support

[Glide]: <https://github.com/bumptech/glide>

[FastScroll]: <https://github.com/L4Digital/FastScroll>

[Header-decor]: <https://github.com/edubarr/header-decor>

[CameraX]: <https://developer.android.com/training/camerax>

[Coroutines]: <https://developer.android.com/kotlin/coroutines>
