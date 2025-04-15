import $ from "jquery";

export default {
  state:{
    id: "",
    username:"",
    gender: "",
    incomeLevel: "",
    travelModePreference: "",
    activityTypePreference: "",
    travelRestriction: "",
    rests: "",
    is_login:false,
    token:""
  },
  getters:{},
  mutations:{
    updateUser(state,user){
      state.id = user.id;
      state.username = user.username;
      state.gender = user.gender;
      state.incomeLevel = user.incomeLevel;
      state.travelModePreference = user.travelModePreference;
      state.activityTypePreference = user.activityTypePreference;
      state.travelRestriction = user.travelRestriction;
      state.rests = user.rests;
      state.is_login = true;
    },
    updateToken(state,token){
      state.token = token;
    }
  },
  actions:{
    login(context,data){
      $.ajax({
        url:"http://127.0.0.1:8081/user/account/token/",
        type:"post",
        data:{
          username:data.username,
          password:data.password
        },
        success(resp){
          if(resp.error_message == "success") {
            localStorage.setItem("jwt_token",resp.token);
            context.commit("updateToken", resp.token);
            data.success(resp);
          }else{
            data.error(resp);
          }
        },
        error(resp) {
          data.error(resp);
        }});
    },
    getinfo(context,data){
      $.ajax({
        url:"http://127.0.0.1:8081/user/account/info/",
        type:"get",
        headers:{
          Authorization: "Bearer " + context.state.token,
        },
        success(resp){
          if(resp.error_message == "success") {
            context.commit("updateUser", {
              ...resp,
              is_login: true
            });
            data.success(resp);
          }else {
            data.error(resp);
          }
        },
        error(resp) {
          data.error(resp)
        }})
    }

  },
  modules:{}

}
