package geym.zbase.my;

class Human {
}

class Man extends Human {
}

class Woman extends Human {
}

public class StaticPai {

    public void say(Human hum) {
        System.out.println("I am human");
    }

    public void say(Man hum) {
        System.out.println("I am man");
    }

    public void say(Woman hum) {
        System.out.println("I am woman");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        StaticPai sp = new StaticPai();
        sp.say(man);
        sp.say(woman);
    }
}