package com.example.uiwidgettest.designpattern.support;

/**
 * Created by 40774 on 2017/11/3.
 */

public class DesignDataSupport {
    public static final String SIMPLEFACTORY_INTRODUCE="  简单工厂模式又称静态工厂方法模式。重命名上就可以看出这个模式一定很简单。它存在的目的很简单：定义一个用于创建对象的接口。 \n" +
            "      先来看看它的组成： \n" +
            "         1) 工厂类角色：这是本模式的核心，含有一定的商业逻辑和判断逻辑，用来创建产品\n" +
            "         2) 抽象产品角色：它一般是具体产品继承的父类或者实现的接口。         \n" +
            "         3) 具体产品角色：工厂类所创建的对象就是此角色的实例。在java中由一个具体类实现。 \n" +
            "        \n" +
            "        下面我们从开闭原则（对扩展开放；对修改封闭）上来分析下简单工厂模式。当客户不再满足现有的电脑型号的时候" +
            "，想要一种速度快的电脑，只要这种电脑符合抽象产品制定的合同，那么只要通知工厂类知道就可以被客户使用了。所以对产品部分来说，它是符合开闭原则的；但是工厂部分好像不太理想" +
            "，因为每增加一种新型电脑，都要在工厂类中增加相应的创建业务逻辑，这显然是违背开闭原则的。可想而知对于新产品的加入，工厂类是很被动的。对于这样的工厂类，我们称它为全能类或者上帝类。 ";
    public static final String SINGLETON_INTRODUCE="单例模式\n  " +
            "概念：\n" +
            "　　java中单例模式是一种常见的设计模式，单例模式的写法有好几种，这里主要介绍三种：懒汉式单例、饿汉式单例、登记式单例。\n" +
            "　　单例模式有以下特点：\n" +
            "　　1、单例类只能有一个实例。\n" +
            "　　2、单例类必须自己创建自己的唯一实例。\n" +
            "　　3、单例类必须给所有其他对象提供这一实例。\n" +
            "　　单例模式确保某个类只有一个实例，而且自行实例化并向整个系统提供这个实例。在计算机系统中，线程池、缓存、日志对象、对话框、打印机、显卡的驱动程序对象常被设计成单例。这些应用都或多或少具有资源管理器的功能。每台计算机可以有若干个打印机，但只能有一个Printer Spooler，以避免两个打印作业同时输出到打印机中。每台计算机可以有若干通信端口，系统应当集中管理这些通信端口，以避免一个通信端口同时被两个请求同时调用。总之，选择单例模式就是为了避免不一致状态，避免政出多头。\n" +
            "\n" +
            "一、懒汉式单例\n" +
            "[java] view plain copy\n" +
            "//懒汉式单例类.在第一次调用的时候实例化自己   \n" +
            "public class Singleton {  \n" +
            "    private Singleton() {}  \n" +
            "    private static Singleton single=null;  \n" +
            "    //静态工厂方法   \n" +
            "    public static Singleton getInstance() {  \n" +
            "         if (single == null) {    \n" +
            "             single = new Singleton();  \n" +
            "         }    \n" +
            "        return single;  \n" +
            "    }  \n" +
            "}  \n" +
            "\n" +
            "Singleton通过将构造方法限定为private避免了类在外部被实例化，在同一个虚拟机范围内，Singleton的唯一实例只能通过getInstance()方法访问。\n" +
            "（事实上，通过Java反射机制是能够实例化构造方法为private的类的，那基本上会使所有的Java单例实现失效。此问题在此处不做讨论，姑且掩耳盗铃地认为反射机制不存在。）\n" +
            "但是以上懒汉式单例的实现没有考虑线程安全问题，它是线程不安全的，并发环境下很可能出现多个Singleton实例，要实现线程安全，有以下三种方式，都是对getInstance这个方法改造，保证了懒汉式单例的线程安全，如果你第一次接触单例模式，对线程安全不是很了解，可以先跳过下面这三小条，去看饿汉式单例，等看完后面再回头考虑线程安全的问题：\n" +
            "\n" +
            "1、在getInstance方法上加同步\n" +
            "[java] view plain copy\n" +
            "public static synchronized Singleton getInstance() {  \n" +
            "         if (single == null) {    \n" +
            "             single = new Singleton();  \n" +
            "         }    \n" +
            "        return single;  \n" +
            "}  \n" +
            "\n" +
            "2、双重检查锁定\n" +
            "[java] view plain copy\n" +
            "public static Singleton getInstance() {  \n" +
            "        if (singleton == null) {    \n" +
            "            synchronized (Singleton.class) {    \n" +
            "               if (singleton == null) {    \n" +
            "                  singleton = new Singleton();   \n" +
            "               }    \n" +
            "            }    \n" +
            "        }    \n" +
            "        return singleton;   \n" +
            "    }  \n" +
            "3、静态内部类\n" +
            "[java] view plain copy\n" +
            "public class Singleton {    \n" +
            "    private static class LazyHolder {    \n" +
            "       private static final Singleton INSTANCE = new Singleton();    \n" +
            "    }    \n" +
            "    private Singleton (){}    \n" +
            "    public static final Singleton getInstance() {    \n" +
            "       return LazyHolder.INSTANCE;    \n" +
            "    }    \n" +
            "}    \n" +
            "这种比上面1、2都好一些，既实现了线程安全，又避免了同步带来的性能影响。\n" +
            "\n" +
            "\n" +
            "二、饿汉式单例\n" +
            "[java] view plain copy\n" +
            "//饿汉式单例类.在类初始化时，已经自行实例化   \n" +
            "public class Singleton1 {  \n" +
            "    private Singleton1() {}  \n" +
            "    private static final Singleton1 single = new Singleton1();  \n" +
            "    //静态工厂方法   \n" +
            "    public static Singleton1 getInstance() {  \n" +
            "        return single;  \n" +
            "    }  \n" +
            "}  \n" +
            "饿汉式在类创建的同时就已经创建好一个静态的对象供系统使用，以后不再改变，所以天生是线程安全的。\n" +
            "\n" +
            "\n" +
            "三、登记式单例(可忽略)\n" +
            "[java] view plain copy\n" +
            "//类似Spring里面的方法，将类名注册，下次从里面直接获取。  \n" +
            "public class Singleton3 {  \n" +
            "    private static Map<String,Singleton3> map = new HashMap<String,Singleton3>();  \n" +
            "    static{  \n" +
            "        Singleton3 single = new Singleton3();  \n" +
            "        map.put(single.getClass().getName(), single);  \n" +
            "    }  \n" +
            "    //保护的默认构造子  \n" +
            "    protected Singleton3(){}  \n" +
            "    //静态工厂方法,返还此类惟一的实例  \n" +
            "    public static Singleton3 getInstance(String name) {  \n" +
            "        if(name == null) {  \n" +
            "            name = Singleton3.class.getName();  \n" +
            "            System.out.println(\"name == null\"+\"--->name=\"+name);  \n" +
            "        }  \n" +
            "        if(map.get(name) == null) {  \n" +
            "            try {  \n" +
            "                map.put(name, (Singleton3) Class.forName(name).newInstance());  \n" +
            "            } catch (InstantiationException e) {  \n" +
            "                e.printStackTrace();  \n" +
            "            } catch (IllegalAccessException e) {  \n" +
            "                e.printStackTrace();  \n" +
            "            } catch (ClassNotFoundException e) {  \n" +
            "                e.printStackTrace();  \n" +
            "            }  \n" +
            "        }  \n" +
            "        return map.get(name);  \n" +
            "    }  \n" +
            "    //一个示意性的商业方法  \n" +
            "    public String about() {      \n" +
            "        return \"Hello, I am RegSingleton.\";      \n" +
            "    }      \n" +
            "    public static void main(String[] args) {  \n" +
            "        Singleton3 single3 = Singleton3.getInstance(null);  \n" +
            "        System.out.println(single3.about());  \n" +
            "    }  \n" +
            "}  \n" +
            " 登记式单例实际上维护了一组单例类的实例，将这些实例存放在一个Map（登记薄）中，对于已经登记过的实例，则从Map直接返回，对于没有登记的，则先登记，然后返回。 \n" +
            "这里我对登记式单例标记了可忽略，我的理解来说，首先它用的比较少，另外其实内部实现还是用的饿汉式单例，因为其中的static方法块，它的单例在类被装载的时候就被实例化了。\n" +
            "\n" +
            "饿汉式和懒汉式区别\n" +
            "从名字上来说，饿汉和懒汉，\n" +
            "饿汉就是类一旦加载，就把单例初始化完成，保证getInstance的时候，单例是已经存在的了，\n" +
            "而懒汉比较懒，只有当调用getInstance的时候，才回去初始化这个单例。\n" +
            "另外从以下两点再区分以下这两种方式：\n" +
            "\n" +
            "1、线程安全：\n" +
            "饿汉式天生就是线程安全的，可以直接用于多线程而不会出现问题，\n" +
            "懒汉式本身是非线程安全的，为了实现线程安全有几种写法，分别是上面的1、2、3，这三种实现在资源加载和性能方面有些区别。\n" +
            "\n" +
            "\n" +
            "2、资源加载和性能：\n" +
            "饿汉式在类创建的同时就实例化一个静态对象出来，不管之后会不会使用这个单例，都会占据一定的内存，但是相应的，在第一次调用时速度也会更快，因为其资源已经初始化完成，\n" +
            "而懒汉式顾名思义，会延迟加载，在第一次使用该单例的时候才会实例化对象出来，第一次调用时要做初始化，如果要做的工作比较多，性能上会有些延迟，之后就和饿汉式一样了。\n" +
            "至于1、2、3这三种实现又有些区别，\n" +
            "第1种，在方法调用上加了同步，虽然线程安全了，但是每次都要同步，会影响性能，毕竟99%的情况下是不需要同步的，\n" +
            "第2种，在getInstance中做了两次null检查，确保了只有第一次调用单例的时候才会做同步，这样也是线程安全的，同时避免了每次都同步的性能损耗\n" +
            "第3种，利用了classloader的机制来保证初始化instance时只有一个线程，所以也是线程安全的，同时没有性能损耗，所以一般我倾向于使用这一种。\n" +
            "\n" +
            "什么是线程安全？\n" +
            "如果你的代码所在的进程中有多个线程在同时运行，而这些线程可能会同时运行这段代码。如果每次运行结果和单线程运行的结果是一样的，而且其他的变量的值也和预期的是一样的，就是线程安全的。\n" +
            "或者说：一个类或者程序所提供的接口对于线程来说是原子操作，或者多个线程之间的切换不会导致该接口的执行结果存在二义性,也就是说我们不用考虑同步的问题，那就是线程安全的。\n" +
            "\n" +
            "应用\n" +
            "以下是一个单例类使用的例子，以懒汉式为例，这里为了保证线程安全，使用了双重检查锁定的方式：\n" +
            "[java] view plain copy\n" +
            "public class TestSingleton {  \n" +
            "    String name = null;  \n" +
            "  \n" +
            "        private TestSingleton() {  \n" +
            "    }  \n" +
            "  \n" +
            "    private static volatile TestSingleton instance = null;  \n" +
            "  \n" +
            "    public static TestSingleton getInstance() {  \n" +
            "           if (instance == null) {    \n" +
            "             synchronized (TestSingleton.class) {    \n" +
            "                if (instance == null) {    \n" +
            "                   instance = new TestSingleton();   \n" +
            "                }    \n" +
            "             }    \n" +
            "           }   \n" +
            "           return instance;  \n" +
            "    }  \n" +
            "  \n" +
            "    public String getName() {  \n" +
            "        return name;  \n" +
            "    }  \n" +
            "  \n" +
            "    public void setName(String name) {  \n" +
            "        this.name = name;  \n" +
            "    }  \n" +
            "  \n" +
            "    public void printInfo() {  \n" +
            "        System.out.println(\"the name is \" + name);  \n" +
            "    }  \n" +
            "  \n" +
            "}  \n" +
            "可以看到里面加了volatile关键字来声明单例对象，既然synchronized已经起到了多线程下原子性、有序性、可见性的作用，为什么还要加volatile呢，原因已经在下面评论中提到，\n" +
            "\n" +
            "[java] view plain copy\n" +
            "public class TMain {  \n" +
            "    public static void main(String[] args){  \n" +
            "        TestStream ts1 = TestSingleton.getInstance();  \n" +
            "        ts1.setName(\"jason\");  \n" +
            "        TestStream ts2 = TestSingleton.getInstance();  \n" +
            "        ts2.setName(\"0539\");  \n" +
            "          \n" +
            "        ts1.printInfo();  \n" +
            "        ts2.printInfo();  \n" +
            "          \n" +
            "        if(ts1 == ts2){  \n" +
            "            System.out.println(\"创建的是同一个实例\");  \n" +
            "        }else{  \n" +
            "            System.out.println(\"创建的不是同一个实例\");  \n" +
            "        }  \n" +
            "    }  \n" +
            "}  \n" +
            " 运行结果：\n" +
            "the name is 0539\n"+
            "the name is 0539\n"+
            "创建的是同一实例\n"+
            "\n" +
            "结论：由结果可以得知单例模式为一个面向对象的应用程序提供了对象惟一的访问点，不管它实现何种功能，整个应用程序都会同享一个实例对象。\n" +
            "对于单例模式的几种实现方式，知道饿汉式和懒汉式的区别，线程安全，资源加载的时机，还有懒汉式为了实现线程安全的3种方式的细微差别。";
public static final String FACTORY_INTRODUCE="工厂方法模式（FACTORY METHOD）是一种常用的对象创建型设计模式,此模式的核心精神是封装类中不变的部分，提取其中个性化善变的部分为独立类，通过依赖注入以达到解耦、复用和方便后期维护拓展的目的。它的核心结构有四个角色，分别是抽象工厂；具体工厂；抽象产品；具体产品[1] \n" +
        "中文名 工厂方法模式 外文名 Factory Method 角    色 抽象与具体工厂，抽象与具体产品 应    用 软件设计\n" +
        "工厂方法(Factory Method)模式的意义是定义一个创建产品对象的工厂接口，将实际创建工作推迟到子类当中。核心工厂类不再负责产品的创建，这样核心类成为一个抽象工厂角色，仅负责具体工厂子类必须实现的接口，这样进一步抽象化的好处是使得工厂方法模式可以使系统在不修改具体工厂角色的情况下引进新的产品。\n" +
        "工厂方法模式是简单工厂模式的衍生，解决了许多简单工厂模式的问题。首先完全实现‘开－闭 原则’，实现了可扩展。其次更复杂的层次结构，可以应用于产品结果复杂的场合。[2] \n" +
        "工厂方法模式对简单工厂模式进行了抽象。有一个抽象的Factory类（可以是抽象类和接口），这个类将不再负责具体的产品生产，而是只制定一些规范，具体的生产工作由其子类去完成。在这个模式中，工厂类和产品类往往可以依次对应。即一个抽象工厂对应一个抽象产品，一个具体工厂对应一个具体产品，这个具体的工厂就负责生产对应的产品。\n" +
        "工厂方法模式(Factory Method pattern)是最典型的模板方法模式(Template Method pattern)应用。\n" +
        "角色结构编辑\n" +
        "抽象工厂(Creator)角色：是工厂方法模式的核心，与应用程序无关。任何在模式中创建的对象的工厂类必须实现这个接口。\n" +
        "具体工厂(Concrete Creator)角色：这是实现抽象工厂接口的具体工厂类，包含与应用程序密切相关的逻辑，并且受到应用程序调用以创建产品对象。在上图中有两个这样的角色：BulbCreator与TubeCreator。\n" +
        "抽象产品(Product)角色：工厂方法模式所创建的对象的超类型，也就是产品对象的共同父类或共同拥有的接口。在上图中，这个角色是Light。\n" +
        "具体产品(Concrete Product)角色：这个角色实现了抽象产品角色所定义的接口。某具体产品有专门的具体工厂创建，它们之间往往一一对应。\n" +
        "模式应用编辑\n" +
        "工厂方法经常用在以下两种情况中:\n" +
        "第一种情况是对于某个产品，调用者清楚地知道应该使用哪个具体工厂服务，实例化该具体工厂，生产出具体的产品来。Java Collection中的iterator() 方法即属于这种情况。\n" +
        "第二种情况，只是需要一种产品，而不想知道也不需要知道究竟是哪个工厂为生产的，即最终选用哪个具体工厂的决定权在生产者一方，它们根据当前系统的情况来实例化一个具体的工厂返回给使用者，而这个决策过程这对于使用者来说是透明的。";
    public static final String BUILDERMODE_INTRODUCE="定义：\n" +
            "建造者模式：将一个复杂的对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。\n" +
            "\n" +
            "实用范围\n" +
            "1、当创建复杂对象的算法应该独立于该对象的组成部分以及它们的装配方式时。\n" +
            "2、当构造过程必须允许被构造的对象有不同表示时。\n" +
            "\n" +
            "角色\n" +
            "在这样的设计模式中，有以下几个角色：\n" +
            "1、Builder：为创建一个产品对象的各个部件指定抽象接口。\n" +
            "2、ConcreteBuilder：实现Builder的接口以构造和装配该产品的各个部件，定义并明确它所创建的表示，并提供一个检索产品的接口。\n" +
            "3、Director：构造一个使用Builder接口的对象，指导构建过程。\n" +
            "4、Product：表示被构造的复杂对象。ConcreteBuilder创建该产品的内部表示并定义它的装配过程，包含定义组成部件的类，包括将这些部件装配成最终产品的接口。\n" +
            "\n" +
            "角色Builder：\n" +
            "[java] view plain copy\n" +
            "public interface PersonBuilder {  \n" +
            "     void buildHead();  \n" +
            "     void buildBody();  \n" +
            "     void buildFoot();  \n" +
            "     Person buildPerson();  \n" +
            "}  \n" +
            "\n" +
            "角色ConcreteBuilder：\n" +
            "[java] view plain copy\n" +
            "public class ManBuilder implements PersonBuilder {  \n" +
            "     Person person;  \n" +
            "     public ManBuilder() {  \n" +
            "          person = new Man();  \n" +
            "     }  \n" +
            "     public void buildbody() {  \n" +
            "          person.setBody(\"建造男人的身体\");  \n" +
            "     }  \n" +
            "     public void buildFoot() {  \n" +
            "          person.setFoot(\"建造男人的脚\");  \n" +
            "     }  \n" +
            "     public void buildHead() {  \n" +
            "          person.setHead(\"建造男人的头\");  \n" +
            "     }  \n" +
            "     public Person buildPerson() {  \n" +
            "          return person;  \n" +
            "     }  \n" +
            "}  \n" +
            "\n" +
            "角色ConcreteBuilder：\n" +
            "[java] view plain copy\n" +
            "public class WomanBuilder implements PersonBuilder {  \n" +
            "     Person person;  \n" +
            "     public WomanBuilder() {  \n" +
            "          person = new Woman();  \n" +
            "     }  \n" +
            "     public void buildbody() {  \n" +
            "          person.setBody(“建造女人的身体\");  \n" +
            "     }  \n" +
            "     public void buildFoot() {  \n" +
            "          person.setFoot(“建造女人的脚\");  \n" +
            "     }  \n" +
            "     public void buildHead() {  \n" +
            "          person.setHead(“建造女人的头\");  \n" +
            "     }  \n" +
            "     public Person buildPerson() {  \n" +
            "          return person;  \n" +
            "     }  \n" +
            "}  \n" +
            "\n" +
            "角色Director：\n" +
            "[java] view plain copy\n" +
            "public class PersonDirector {  \n" +
            "     public Person constructPerson(PersonBuilder pb) {  \n" +
            "          pb.buildHead();  \n" +
            "          pb.buildBody();  \n" +
            "          pb.buildFoot();  \n" +
            "          return pb.buildPerson();  \n" +
            "     }  \n" +
            "}  \n" +
            "\n" +
            "角色Product：\n" +
            "[java] view plain copy\n" +
            "public class Person {  \n" +
            "     private String head;  \n" +
            "     private String body;  \n" +
            "     private String foot;  \n" +
            "  \n" +
            "     public String getHead() {  \n" +
            "          return head;  \n" +
            "     }  \n" +
            "     public void setHead(String head) {  \n" +
            "          this.head = head;  \n" +
            "     }  \n" +
            "     public String getBody() {  \n" +
            "          return body;  \n" +
            "     }  \n" +
            "     public void setBody(String body) {  \n" +
            "          this.body = body;  \n" +
            "     }  \n" +
            "     public String getFoot() {  \n" +
            "          return foot;  \n" +
            "     }  \n" +
            "     public void setFoot(String foot) {  \n" +
            "          this.foot = foot;  \n" +
            "     }  \n" +
            "}  \n" +
            "public class Man extends Person {  \n" +
            "     public Man(){  \n" +
            "          System.out.println(“开始建造男人\");  \n" +
            "     }  \n" +
            "}  \n" +
            "public class Woman extends Person {  \n" +
            "     public Woman(){  \n" +
            "          System.out.println(“开始建造女人\");  \n" +
            "     }  \n" +
            "}  \n" +
            "\n" +
            "测试：\n" +
            "[java] view plain copy\n" +
            "public class Test{  \n" +
            "     public static void main(String[] args) {  \n" +
            "          PersonDirector pd = new PersonDirector();  \n" +
            "          Person womanPerson = pd.constructPerson(new ManBuilder());  \n" +
            "          Person manPerson = pd.constructPerson(new WomanBuilder());  \n" +
            "     }  \n" +
            "}  \n" +
            "\n" +
            "建造者模式在使用过程中可以演化出多种形式：\n" +
            "如果具体的被建造对象只有一个的话，可以省略抽象的Builder和Director，让ConcreteBuilder自己扮演指导者和建造者双重角色，甚至ConcreteBuilder也可以放到Product里面实现。\n" +
            "在《Effective Java》书中第二条，就提到“遇到多个构造器参数时要考虑用构建器”，其实这里的构建器就属于建造者模式，只是里面把四个角色都放到具体产品里面了。\n" +
            "\n" +
            "上面例子如果只制造男人，演化后如下：\n" +
            "[java] view plain copy\n" +
            "public class Man {  \n" +
            "     private String head;  \n" +
            "     private String body;  \n" +
            "     private String foot;  \n" +
            "  \n" +
            "     public String getHead() {  \n" +
            "          return head;  \n" +
            "     }  \n" +
            "     public void setHead(String head) {  \n" +
            "          this.head = head;  \n" +
            "     }  \n" +
            "     public String getBody() {  \n" +
            "          return body;  \n" +
            "     }  \n" +
            "     public void setBody(String body) {  \n" +
            "          this.body = body;  \n" +
            "     }  \n" +
            "     public String getFoot() {  \n" +
            "          return foot;  \n" +
            "     }  \n" +
            "     public void setFoot(String foot) {  \n" +
            "          this.foot = foot;  \n" +
            "     }  \n" +
            "}  \n" +
            "\n" +
            "[java] view plain copy\n" +
            "public class ManBuilder{  \n" +
            "     Man man;  \n" +
            "     public ManBuilder() {  \n" +
            "          man = new Man();  \n" +
            "     }  \n" +
            "     public void buildbody() {  \n" +
            "          man.setBody(\"建造男人的身体\");  \n" +
            "     }  \n" +
            "     public void buildFoot() {  \n" +
            "          man.setFoot(\"建造男人的脚\");  \n" +
            "     }  \n" +
            "     public void buildHead() {  \n" +
            "          man.setHead(\"建造男人的头\");  \n" +
            "     }  \n" +
            "     public Man builderMan() {  \n" +
            "          buildHead();  \n" +
            "          buildBody();  \n" +
            "          buildFoot();  \n" +
            "          return man;  \n" +
            "     }  \n" +
            "}  \n" +
            "\n" +
            "测试：\n" +
            "[java] view plain copy\n" +
            "public class Test{  \n" +
            "     public static void main(String[] args) {  \n" +
            "          ManBuilder builder = new ManBuilder();  \n" +
            "          Man man = builder.builderMan();  \n" +
            "     }  \n" +
            "}  ";
}
