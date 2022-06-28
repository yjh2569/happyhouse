"use strict";(self["webpackChunkfinal_project_vue"]=self["webpackChunkfinal_project_vue"]||[]).push([[689],{499:function(t,e,n){n.r(e),n.d(e,{default:function(){return $}});var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-container",[n("div",{staticClass:"text-center"},[n("h1",{staticClass:"ma-8"},[t._v("뉴스")])]),n("div",[n("v-row",[n("v-col",{attrs:{cols:"3"}},[n("v-text-field",{staticClass:"ml-1 form-control",attrs:{type:"text","append-icon":"mdi-magnify",label:"Search","single-line":"","hide-details":""},on:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.search.apply(null,arguments)}},model:{value:t.word,callback:function(e){t.word=e},expression:"word"}})],1),n("v-spacer")],1)],1),t.items.length?n("div",[n("v-simple-table",{staticClass:"text-center",scopedSlots:t._u([{key:"default",fn:function(){return[n("thead",[n("tr",{staticClass:"text-center"},[n("th",{staticClass:"text-center"},[t._v("제목")]),n("th",{staticClass:"text-center"},[t._v("작성 시간")])])]),n("tbody",t._l(t.items,(function(t,e){return n("news-list-item",{key:e,attrs:{item:t}})})),1)]},proxy:!0}],null,!1,25509147)}),n("news-pagination-content",{attrs:{word:t.word},on:{pagechanged:t.pagechanged}})],1):n("div",{staticClass:"text-center"},[t._v(t._s(t.noItemsMsg))])])},s=[],a=n(6927);const o=(0,a.b)();function r(t,e,n){o.get("/news",{params:t}).then(e).catch(n)}function h(t,e,n){o.get("/news/count",{params:t}).then(e).catch(n)}var l=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("tr",[n("td",[n("a",{attrs:{href:t.item.link,target:"_blank"},domProps:{innerHTML:t._s(t.item.title)}})]),n("td",[t._v(t._s(t._f("formatDate")(t.item.pubDate)))])])},u=[],c=n(381),d=n.n(c),g={name:"NewsListItem",props:{item:Object},filters:{formatDate(t){return d()(new Date(t)).format("YYYY.MM.DD hh:mm A")}}},p=g,m=n(1001),C=(0,m.Z)(p,l,u,!1,null,null,null),x=C.exports,I=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"text-center"},[n("v-pagination",{attrs:{length:t.pageCount,circle:""},on:{input:t.movePage},model:{value:t.currentPageIndex,callback:function(e){t.currentPageIndex=e},expression:"currentPageIndex"}})],1)},f=[],v={name:"paginationContent",props:{target:String,searchKey:String,word:String,changed:Boolean},data(){return{totalListItemCount:0,listRowCount:10,pageLinkCount:10,currentPageIndex:1,pageCount:0,startPageIndex:0,endPageIndex:0,prev:!1,next:!1}},methods:{movePage(t){this.currentPageIndex=t,this.$emit("pagechanged",t),this.initComponent()},initComponent(){let t={query:this.word};""!=this.word&&h(t,(({data:t})=>{this.totalListItemCount=t,this.initUI()}),(()=>{alert("에러가 발생했습니다.")}))},initUI(){this.pageCount=Math.ceil(this.totalListItemCount/this.listRowCount),this.pageCount=Math.min(10,this.pageCount),this.currentPageIndex%this.pageLinkCount==0?this.startPageIndex=(this.currentPageIndex/this.pageLinkCount-1)*this.pageLinkCount+1:this.startPageIndex=Math.floor(this.currentPageIndex/this.pageLinkCount)*this.pageLinkCount+1,this.currentPageIndex%this.pageLinkCount==0?this.endPageIndex=(this.currentPageIndex/this.pageLinkCount-1)*this.pageLinkCount+this.pageLinkCount:this.endPageIndex=Math.floor(this.currentPageIndex/this.pageLinkCount)*this.pageLinkCount+this.pageLinkCount,this.endPageIndex>this.pageCount&&(this.endPageIndex=this.pageCount),this.currentPageIndex<=this.pageLinkCount?this.prev=!1:this.prev=!0,this.endPageIndex>=this.pageCount?(this.endPageIndex=this.pageCount,this.next=!1):this.next=!0}},watch:{currentPageIndex:function(){this.initUI()},word:function(){this.initComponent()}},created(){this.initComponent()}},w=v,k=n(3453),P=n.n(k),_=n(6325),L=(0,m.Z)(w,I,f,!1,null,null,null),y=L.exports;P()(L,{VPagination:_.Z});var M={components:{NewsListItem:x,NewsPaginationContent:y},data(){return{pg:1,word:"",items:[],noItemsMsg:"뉴스를 가져오는 중..."}},created(){this.word="부동산";let t={query:this.word,start:1};""!=this.word&&r(t,(t=>{this.items=t.data,0==this.items.length&&(this.noItemsMsg="오류로 인해 뉴스를 가져오지 못했습니다.")}),(t=>{console.log(t),this.noItemsMsg="오류로 인해 뉴스를 가져오지 못했습니다."}))},methods:{search(){let t={query:this.word,start:1};""!=this.word&&r(t,(t=>{this.items=t.data,0==this.items.length&&(this.noItemsMsg="검색 결과가 없습니다.")}),(t=>{console.log(t),this.noItemsMsg="오류로 인해 뉴스를 가져오지 못했습니다."}))},pagechanged(t){let e={start:10*(t-1)+1,query:this.word};""!=this.word&&r(e,(t=>{this.items=t.data}),(t=>{console.log(t),this.noItemsMsg="오류로 인해 뉴스를 가져오지 못했습니다."}))}}},b=M,Z=n(2102),S=n(9846),V=n(2877),D=n(3568),q=n(9762),E=n(314),Y=(0,m.Z)(b,i,s,!1,null,"04546e8d",null),$=Y.exports;P()(Y,{VCol:Z.Z,VContainer:S.Z,VRow:V.Z,VSimpleTable:D.Z,VSpacer:q.Z,VTextField:E.Z})}}]);
//# sourceMappingURL=689.c17bdb2a.js.map