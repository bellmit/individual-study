package cn.gyw.corejava.enumrate;

import cn.gyw.corejava.clz.generator.Generator;

import java.util.EnumMap;
import java.util.Random;

import static cn.gyw.corejava.enumrate.Input.*;


/**
 * 自动售货机实例
 *
 * 1.enum 实现状态的转换
 */
public class VendingMachine {

    private static State state = State.RESTING;
    private static int amount = 100;
    private static Input selection = null;

    public static void main(String[] args) {
        Generator<Input> gen = new RandomInputGenerator();
        run(gen);
    }

    private static void run(Generator<Input> gen) {
        while (state != State.TERMINAL) {
            if (amount <= 0) {
                break;
            }
            state.next(gen.next());
            while (state.isTransient)
                state.next();
            state.output();
        }
    }

    /*
    瞬时状态枚举
     */
    enum StateDuration {
        TRANSIENT
    }

    /*
    状态枚举
     */
    enum State {
        // 停止状态
        RESTING {
            void next(Input input) {
                switch (Category.categorize(input)) {
                    case MONEY:
                        amount += input.amount();
                        state = ADDING_MONEY;
                        break;
                    case SHUT_DOWN:
                        state = TERMINAL;
                    default:
                }
            }
        },
        // 投币
        ADDING_MONEY {
            void next(Input input) {
                switch (Category.categorize(input)) {
                    case MONEY:
                        amount += input.amount();
                        break;
                    case ITEM_SELECTION:
                        selection = input;
                        if (amount < selection.amount())
                            System.out.println("Insufficient money for " + selection);
                        else
                            state = DISPENSING;
                        break;
                    case QUIT_TRANSACTION:
                        state = GIVING_CHANGE;
                        break;
                    case SHUT_DOWN:
                        state = TERMINAL;
                    default:
                }
            }
        },
        // 分配
        DISPENSING(StateDuration.TRANSIENT) {
            void next() {
                System.out.println("here is your " + selection);
                amount -= selection.amount();
                state = GIVING_CHANGE;
            }
        },
        // 找零
        GIVING_CHANGE(StateDuration.TRANSIENT) {
            void next() {
                if (amount > 0) {
                    System.out.println("your change :" + amount);
                    amount = 0;
                }
                state = RESTING;
            }
        },
        // 结束
        TERMINAL {
            void output() {
                System.out.println("Halted");
            }
        };

        // 是否瞬时状态
        private boolean isTransient = false;

        State() {

        }

        State(StateDuration trans) {
            isTransient = true;
        }

        void next(Input input) {
            throw new RuntimeException("only call next(input) for non-transient states");
        }

        void next() {
            throw new RuntimeException("only call next() for stateDuration.TRANSIENT states");
        }

        void output() {
            System.out.println(amount);
        }
    }
}

/**
 * 状态机：自动售货机实例
 * <p>
 * 说明：
 * 1.模仿 JDBCType 枚举的使用
 * 2.用一个enum定义各种输入
 */
enum Input {
    NICKEL(5),
    DIME(10),
    QUARTER(25),
    DOLLAR(100),
    TOOTHPASTE(200),
    CHIPS(75),
    SODA(100),
    SOAP(50),
    ABORT_TRANSACTION {
        public int amount() {
            throw new RuntimeException("Abort.amount()");
        }
    },
    STOP {
        public int amount() {
            throw new RuntimeException("SHUT_DOWN.amount()");
        }
    };

    private int value;

    Input() {
    }

    Input(int value) {
        this.value = value;
    }

    int amount() {
        return value;
    }

    static Random rand = new Random(47);

    public static Input randomSelection() {
        int index = rand.nextInt(values().length - 1);
        System.out.println("Input random index : " + index);
        return values()[index];
    }
}

/**
 * 第一个反应将其归类为Category enum中的某个enum实例
 */
enum Category {
    // 钱类常量枚举
    MONEY(NICKEL, DIME, QUARTER, DOLLAR),
    // 商品栏目枚举
    ITEM_SELECTION(TOOTHPASTE, CHIPS, SODA, SOAP),
    // 停用枚举
    QUIT_TRANSACTION(ABORT_TRANSACTION),
    // 停止枚举
    SHUT_DOWN(STOP);

    private Input[] values;

    Category(Input... types) {
        values = types;
    }

    private final static EnumMap<Input, Category> categories = new EnumMap<>(Input.class);

    static {
        for (Category c : Category.class.getEnumConstants())
            for (Input type : c.values)
                categories.put(type, c);
    }

    public static Category categorize(Input input) {
        return categories.get(input);
    }
}

class RandomInputGenerator implements Generator<Input> {

    @Override
    public Input next() {
        return Input.randomSelection();
    }

}
