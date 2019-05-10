# MCVPDemo
[内聚代码提高逻辑可读性，用MCVP接续你的大逻辑](https://juejin.im/post/5cd4f26f6fb9a0322d04a330)

最近由于感觉和同事的MPV代码在理解上有所差异，我重新审视了一下MVP的写法，对目前项目中出现的一些问题进行了思考，创造出一种MVP的变形，我暂且称它为MCVP。

### MVP ###
>对基础内容不感兴趣的同学可以直接跳到MCVP的部分。


![](https://user-gold-cdn.xitu.io/2019/5/9/16a9bdb4d0490314?w=476&h=253&f=png&s=6920)

这是一张随处可见的MVP模式图，重申一下MVP的基本概念：

**View**一般指Activity和Fragment，它们承载用户的UI界面，和用户直接交互。

**Model**指数据源，它是负责获取和保存数据的对象的集合，而不是特指某一个实体或者叫model的接口，用层的概念来描述它更加合适--**Model层**。
    
**Presenter**是View和Model之间的协调者，负责处理业务逻辑、承接业务逻辑。
    
>View和Presenter之间使用接口调用进行解耦，接口上的方法应该是它们具体行为的抽象：作为调用者去调用对方的方法，它会有一个意图，是 i want to do；而被调用者的暴露方法，就是what i can do的集合。
而Model是否应该使用接口进行抽象呢？我觉得大部分程序都没这个必要，Model接口的维护成本比较高，带来的价值也不大，除非你的数据源有多个实现，例如在不同情况下使用不同的服务器数据库，可以考虑用接口解耦。
    
>MVP模式大致分为两种写法，被动视图(Passive View)和监督控制器(Supervising Controller)。被动视图中View把逻辑和与Model的交互全权交给Presenter，甚至自己的表现也由presenter控制，什么都不需要管；监督控制器中Presenter只是一个逻辑辅助的职能，View会部分接触修改到Model的数据，但是我认为这是被动视图也无法避免的，例如列表显示总要依赖实体类，所以我更倾向于监督控制器写法。
    
以上不过是我个人理解，欢迎互相讨论。
    
### 经常会出现的问题 ###
很多小伙伴对MVP甚至是对面向对象的基本原则理解得不够，于是出现很多奇奇怪怪的问题，包括但不限于以下问题：

1. 创建出IModel接口，为每一个不同的IPresenter接口实现一个Model类，IPresenter和IModel通过Contract关联一起,这样不仅出现了很多Model类，也一并出现了非常多的冗余代码。正如我上面所说，Model实际上是一个集合，一个Model层的概念。这张图更加能说明情况。

![](https://user-gold-cdn.xitu.io/2019/5/9/16a9bddeffaffe4f?w=705&h=446&f=png&s=17652)
2. IView接口和IPresenter接口之间getter setter具体参数的方法过多，违反迪米特原则，通俗地说，就是暴露太多了。辛辛苦苦写几个接口来做MVP，然后加了N个getter setter，是想要把这个接口当作java bean来使用吗？MVP接口的基本就是对行为的抽象，一堆getter setter有抽象可言咩？

![](https://user-gold-cdn.xitu.io/2019/5/9/16a9bde1d510e82b?w=150&h=150&f=gif&s=455603)

3. 和第2点一样的问题，没有对View和Presenter的行为进行抽象，Presenter过多参与控制View的显示，违反单一职责原则，甚至出现对某些个View控件进行直接操作的情况。接口只暴露行为的抽象，具体的操作细节不应该暴露出来。

4. View的全部点击事件使用某种唯一参数传达给Presenter的某个特定方法，在Presenter对参数集中进行判断执行。出现这种情况也是没有对行为进行抽象。View犹如行尸走肉，完全没有i want to do的意图，就像是把石头丢入黑洞，至于黑洞里面发生了什么事、会发生什么事？I don't know。好好想一想，View-UI作为与用户交互的平台，用户从UI点击某个图标的时候，用户到底有没有意图？那么UI的事件应不应该有意图？

![](https://user-gold-cdn.xitu.io/2019/5/9/16a9bde6c1acee57?w=240&h=240&f=jpeg&s=10621)

5. Presenter知晓了Android框架的东西。Intent的数据获取和传递、onActivityResult()的返回处理，这些都是Android框架的部分，应该交回View去处理。往大说是Presenter不要依赖Android环境便于测试，往小说是Intent数据的定义、startActivity()都是activity、fragment的事，你一个Presenter掺和啥呢？

![](https://user-gold-cdn.xitu.io/2019/5/9/16a9be069849e89b?w=234&h=240&f=png&s=97607)

### MCVP ###
上面说了很多奇怪但是不一定会在你项目里面出现了问题，其实它们不是重点，接下来这个问题我相信应该有出现过在你项目里，或者你看过的项目里。

>**场景与需求**：提交订单的页面，提交订单之后会提示可以升级你的订单，升级后能够获得更好的售后服务，但要收取少量服务费，而这个订单是否能够升级，是由后台动态配置。

流程如图所示:

![](https://user-gold-cdn.xitu.io/2019/5/9/16a9be14426fb4b5?w=541&h=731&f=png&s=32967)

代码大概是这样的（伪代码不用太纠结细节，我们注意看思路即可）：


![](https://user-gold-cdn.xitu.io/2019/5/9/16a9be1647e2d9cb?w=803&h=557&f=png&s=70078)

![](https://user-gold-cdn.xitu.io/2019/5/9/16a9be1809bc28cc?w=997&h=597&f=png&s=103308)

![](https://user-gold-cdn.xitu.io/2019/5/9/16a9be195fa23fa2?w=938&h=777&f=png&s=130799)


这发生了两个问题：

1. 割裂。我们的业务代码由于需要View的弹框询问而断裂成submitOrder()和submitUpgradeOrder()两个方法，导致逻辑不连贯。在Presenter的视角来看，提交订单的一系列流程并不是Presenter自己可知可控的，虽然submitUpgradeOrder()
是提交订单流程的一步，但是自己它似乎完全不知道什么时候会被触发，至少从presenter代码来看是这样的。


![](https://user-gold-cdn.xitu.io/2019/5/9/16a9be1b69d22842?w=486&h=418&f=png&s=14401)


2. 不符合被动视图的写法。以被动视图的思路来看，View只需要询问用户是否确认然后告诉Presenter就足够了，甚至不需要知道Presenter要它询问用户确认些什么东西。就像菜贩子和顾客的关系，菜贩只要知道顾客需要什么蔬菜，而不需要知道顾客买这个瓜回去干些什么，完全的解耦关系。

![](https://user-gold-cdn.xitu.io/2019/5/9/16a9be1e040885b4?w=169&h=183&f=png&s=32960)


###### 针对这种特殊场景，我给出的解决方案是：



**MCVP MCVP MCVP MCVP MCVP**



>即 Model-CallbackView-Presenter。
>看到CallbackView这个词，聪明的同学可能已经猜出了答案。MCVP是MVP的一个特殊变体，可以随时运用到你的项目中。

在Presenter这个角色的视角中，询问用户是否升级订单，不过是一个异步获取数据的过程而已，我们完全可以把View看成是一个数据源，仿照网络请求的方式等待用户选择结果返回。

当我们选择这种方案，传递给View的入参变成了带有泛型的回调接口Callback<T>，View只知道我们需要它回答什么（泛型T），它通过Callback接口回调过去即可。


![](https://user-gold-cdn.xitu.io/2019/5/9/16a9be20d2732029?w=517&h=407&f=png&s=14706)

MCVP完全避免了业务逻辑的断裂，保持了流程的连贯，presenter对于逻辑流程完全可知，提 高了逻辑可读性，类中的业务逻辑方法内聚性更加高，你可爱的业务逻辑终于不需要再在View和Presenter的实现上跳来跳去了，刚接手代码的人也简单清楚逻辑流的走向，维护性也提高了；而且完全符合被动视图的写法，View也不会因此接触到任何实体类。解决了上面所述的两个问题。


![](https://user-gold-cdn.xitu.io/2019/5/9/16a9be27467ea5c5?w=240&h=240&f=png&s=120311)

看一下**MCVP**的代码：

![](https://user-gold-cdn.xitu.io/2019/5/9/16a9be296ca90fab?w=997&h=1717&f=png&s=254911)

可以看到，所有业务流程都在submitOrder()中，没有跳出这个方法，submitOrder()非常忠实地完成了它提交订单的任务，没有再假手于人（View）了。

有同学说出现了回调地狱啊~那很好解决，相信大家都知道解决方法，Rxjava、协程...它们都可以解决。演示一个Rxjava的写法：

![](https://user-gold-cdn.xitu.io/2019/5/9/16a9be2b8fc3c228?w=1022&h=1417&f=png&s=223528)


MCVP有它的优点，但我也承认它也有自己的缺点：

![](https://user-gold-cdn.xitu.io/2019/5/9/16a9be2db9171728?w=240&h=240&f=png&s=35353)

1. Callback只能使用一次，而且要保证它不会被调用两次，不然可能发生某些问题，只要知道这个情况存在，基本不会出幺蛾子。
2. 由于Callback的存在，View需要持有Callback这个对象，存放成全局变量（一个即可）或者每次创建你的UI（就像例子中的dialog那样），不过这我觉得其实不算是个问题，全局变量只是个小小的杂质，不影响它带来的优点。

MCVP只不过是CallbackView与MVP的结合，这个思想完全可以移植到其他模式上。除了我的举例，它应该会有更多写法，例如从View方法返回Callback而不是Presenter提供Callback，我也会继续继续思考优化MCVP，期待它能够在更多场景发挥作用，使我们的代码结构变得更加好。


[去GitHub看Demo](https://github.com/SpannerBear/MCVPDemo)

**谢谢你的观看！**

周末看比卡超去吧~!