package cn.gyw.springboot.webmvc.async;

public class UserBehaviorDataDTO {
    private Long fansCount;
    private Long msgCount;
    private Long collectCount;
    private Long followCount;
    private Long redBagCount;
    private Long couponCount;

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long fansCount;
        private Long msgCount;
        private Long collectCount;
        private Long followCount;
        private Long redBagCount;
        private Long couponCount;

        private Builder() {
        }

        public static Builder anUserBehaviorDataDTO() {
            return new Builder();
        }

        public Builder fansCount(Long fansCount) {
            this.fansCount = fansCount;
            return this;
        }

        public Builder msgCount(Long msgCount) {
            this.msgCount = msgCount;
            return this;
        }

        public Builder collectCount(Long collectCount) {
            this.collectCount = collectCount;
            return this;
        }

        public Builder followCount(Long followCount) {
            this.followCount = followCount;
            return this;
        }

        public Builder redBagCount(Long redBagCount) {
            this.redBagCount = redBagCount;
            return this;
        }

        public Builder couponCount(Long couponCount) {
            this.couponCount = couponCount;
            return this;
        }

        public UserBehaviorDataDTO build() {
            UserBehaviorDataDTO userBehaviorDataDTO = new UserBehaviorDataDTO();
            userBehaviorDataDTO.collectCount = this.collectCount;
            userBehaviorDataDTO.followCount = this.followCount;
            userBehaviorDataDTO.redBagCount = this.redBagCount;
            userBehaviorDataDTO.couponCount = this.couponCount;
            userBehaviorDataDTO.fansCount = this.fansCount;
            userBehaviorDataDTO.msgCount = this.msgCount;
            return userBehaviorDataDTO;
        }
    }
}
