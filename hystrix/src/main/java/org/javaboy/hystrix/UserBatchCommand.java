package org.javaboy.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import org.javaboy.commons.User;

import java.util.List;

/**
 * @Author szh
 * @Date 2022/5/29 10:36
 * @PackageName:org.javaboy.hystrix
 * @ClassName: UserBatchCommand
 * @Description: TODO
 * @Version 1.0
 */
public class UserBatchCommand extends HystrixCommand<List<User>> {
    private List<Integer> ids;
    private UserService userService;

    public UserBatchCommand(List<Integer> ids, UserService userService) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("batchCmd")).andCommandKey(HystrixCommandKey.Factory.asKey("bathckey")));
        this.ids = ids;
        this.userService = userService;
    }

    @Override
    protected List<User> run() throws Exception {
        return userService.getUserByIds(ids);
    }
}
