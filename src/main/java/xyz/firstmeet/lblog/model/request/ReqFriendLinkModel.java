package xyz.firstmeet.lblog.model.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xyz.firstmeet.lblog.object.FriendLink;

@ApiModel
@ToString
@Setter
@Getter
public class ReqFriendLinkModel extends FriendLink {
}
