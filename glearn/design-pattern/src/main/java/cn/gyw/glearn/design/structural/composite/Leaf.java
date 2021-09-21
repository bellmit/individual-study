package cn.gyw.glearn.design.structural.composite;

/**
 * Created by guanyw on 2018/7/11.
 */
public class Leaf extends Component {
	public Leaf(String name) {
		super(name);
	}

	@Override
	public void add(Component c) {
		System.out.println("Leaf no add");
	}

	@Override
	public void remove(Component c) {
		System.out.println("Leaf no remove");
	}

	@Override
	public void display(int depth) {
		StringBuffer sb = new StringBuffer("-");
        for (int i = 0; i <= depth; i++) {
            sb.append("-");
        }
        System.out.println(sb.toString()+name);
	}
}
