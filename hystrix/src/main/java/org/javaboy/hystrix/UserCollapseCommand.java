package org.javaboy.hystrix;

import com.netflix.hystrix.*;
import org.javaboy.commons.User;
import org.springframework.cloud.netflix.hystrix.HystrixProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author szh
 * @Date 2022/5/29 10:42
 * @PackageName:org.javaboy.hystrix
 * @ClassName: UserCollapseCommand
 * @Description: TODO
 * @Version 1.0
 */
/**
 * 泛型依次是 批处理返回对象  数据响应类型  方法参数类型
 */
public class UserCollapseCommand extends HystrixCollapser<List<User>,User,Integer> {

    private UserService userService;
    private Integer id;

    public UserCollapseCommand(UserService userService, Integer id) {
        super(HystrixCollapser.Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("UserCollapseCommand")).andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(200)));
        this.userService = userService;
        this.id = id;
    }

    /*请求参数*/
    @Override
    public Integer getRequestArgument() {
        return id;
    }

    /*请求合并的方法*/
    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Integer>> collection) {
        List<Integer> ids = new ArrayList<>(collection.size());
        for (CollapsedRequest<User, Integer> userIntegerCollapsedRequest : collection) {
            ids.add(userIntegerCollapsedRequest.getArgument());
        }
        return new UserBatchCommand(ids, userService);
    }

    /*请求结果分发*/
    /**
     * @param users provider返回结果
     * @param collection  请求对象
     */
    @Override
    protected void mapResponseToRequests(List<User> users, Collection<CollapsedRequest<User, Integer>> collection) {
        int count = 0;
        for (CollapsedRequest<User, Integer> request : collection) {
            request.setResponse(users.get(count++));
        }
    }
}
