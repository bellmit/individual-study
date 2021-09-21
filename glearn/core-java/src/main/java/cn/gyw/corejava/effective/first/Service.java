package cn.gyw.corejava.effective.first;

/**
 * Service provider framework sketch
 * 服务提供者框架
 * 服务访问API可以利用适配器模式，返回比提供者需要的更丰富的服务接口。
 *
 * 简单实现：
 * 一个服务提供者接口和一个默认提供者
 */
// 服务接口
public interface Service {
}

// 提供者接口
interface Provider {

    // 提供具体的服务
    Service newService();
}
