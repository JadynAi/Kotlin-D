# Kotlin-D
![](https://mmbiz.qpic.cn/mmbiz_gif/jqw9LvhdsxJRdy8tPr5s35tNYfwkbEefXtjr2FDSNozBjibibWRe1TH1h31gfOjWsNL2570IlgPdecfBPLicD7Rhg/0?wx_fmt=gif)

**D!**
这是一个会长期，但不定期维护的辅助工具项目。因为UP主也就是本人，脑袋常常会短路。<br>`Kotlin-D`是一个帮助开发者，提高生产效率的项目，主要面向Android移动端的项目。

### 配置远程库
项目的build.gradle配置`JCenter`远程仓库。在需要的地方引用：

```
api 'com.jadynai.ai.kotlind:KotlinD:1.0.6'
```

### 如何使用
#### 使用准备
使用前，请将你的项目中的Application继承`com.jadyn.ai.kotlind.base.BaseApplication`。

```
open class BaseApplication : Application() {
    
    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
```
这是一个非常简单的类，只对外提供了一个`Application`对象。在类似于`getDrawable`或者`getColor`这类方法会用到。

<del>#### Recyclerview
`AcrobatAdapter`是为Recyclerview提供的适配器，使用DSL模式构建。可以灵活配置多Item样式，仅用两三行代码为为Item添加单击、长按、双击事件[使用文档](https://ailo.fun/2018/07/05/2018-07-05-kotlin-adapter/)</del>
> 1.0.6版本开始将适配器从库里移除，确保工具库为一个单纯的工具库存在

#### 各色Drawable
不需要为各种View添加Drawable，而去写一些烦扰的Drawable.xml或者Selector.xml文件，只需在代码中一键配置，轻松快捷。

- 为View添加Press效果：

```
// 资源
View.press(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int)

// 直接设置drawable
View.press(normal: Drawable, press: Drawable)

// 颜色
View.pressColor(normalColor: Int, pressColor: Int)
```

## LICENSE

    Copyright [JadynAi]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

