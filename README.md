# DragPointView

[![](https://jitpack.io/v/javonleee/DragPointView.svg)](https://jitpack.io/#javonleee/DragPointView) [![Build Status](https://travis-ci.org/javonleee/DragPointView.svg?branch=master)](https://travis-ci.org/javonleee/DragPointView) [![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

This is a handy developer to quickly implement drag and drop unread messages, widget, which you can use as you do with TextView, and customize detail effects with extra attributes and method.

## Dependency

Add this in your project `build.gradle` file:

```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

Then, add the library to your module `build.gradle`
```gradle
dependencies {
    compile 'com.github.javonleee:DragPointView:latest.release'
}
```

## Features
- Integrated in TextView, easy to use.
- Bessel curve is used to achieve tensile effect.
- Provide you with rich attributes to customize various effects, such as maximum length of drag and drop, front and rear round radius, curve part color, radius scaling coefficient, etc.
- Drag and drop actions are not restricted by the parent container, and the screen is free to drag and drop.
- Allows settings to release animation, Animator or AnimationDrawable.
- External development status monitoring, monitoring the status of the widget.
- Convenient to clear the widget of the same sign.

## Usage
There is a [sample](https://github.com/javonleee/DragPointView/tree/master/sample) provided which shows how to use the library in a more advanced way, but for completeness, here is all that is required to get DragPointView working:
```xml
<com.javonlee.dragpointview.view.DragPointView
	android:id="@+id/drag_point_view"
	android:layout_width="wrap_content"
        android:layout_height="wrap_content"
	android:background="@drawable/shape_drag_point_red"
	android:gravity="center"
	android:text="66"
	android:textColor="#fff"
	android:textSize="16sp"
	app:centerMinRatio="0.5"
	app:clearSign="test"
	app:colorStretching="#f00"
	app:dragCircleRadius="100dp"
	app:maxDragLength="100dp"
	app:recoveryAnimBounce="0.25" />
```
```java
DragPointView dragPointView = (DragPointView) findViewById(R.id.drag_point_view);
pointView.setRemoveAnim(animationDrawable);
pointView.setOnPointDragListener(listener);
```
That's it!

### Attribute
You can customize the desired effect by setting the following properties in the layout file.
- **maxDragLength**:Within this range, the Bessel rendering section will be shown, with the default value of Math.min (w, h)*3.
- **centerCircleRadius**:The initial radius of the center circle.
- **dragCircleRadius**:The initial radius of the drag circle.
- **centerMinRatio**:When dragging, the center circle becomes smaller and smaller with distance, and the property controls its minimum coefficient range: 0.5f~1.0f.
- r**ecoveryAnimDuration**:If the stretch length is not up to the threshold, the animation will be recovery, specifying the length of the animation.
- **recoveryAnimBounce**:recovery animation bounce factor, range 0.0f~1.0f.
- **colorStretching**:Bessel painted some colors, recommended consistent with the widget background.
- **sign**:Mark the category of this control and use it with clearSign.
- **clearSign**:The clearSign specified widgets are removed at the same time as they are removed.
- **canDrag**:Controls whether or not to allow drag and drop.


## Caution
When the DragPointView that sets the clearSign property is removed, be sure to update the relevant object information in the onRemoveStart or onRemoveEnd callback.For example, set all chat sessions to read.
```java
@Override
public void onRemoveStart(AbsDragPointView view) {
	for (ConversationEntity entity : conversationEntities) {
		entity.setRead(true);
		entity.setMessageNum(0);
        }
}
```


Author
------
email - javonlee@163.com | javonlee999@gmail.com


License
--------

    Copyright 2017 javonlee

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
