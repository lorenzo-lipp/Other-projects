function App() {
  const [user, setUser] = React.useState({
    "login": "loading...",
    "avatar_url": ".",
    "followers": 0,
    "following": 0 });

  const [repos, setRepos] = React.useState([{
    name: 'aaaaa',
    html_url: '#',
    full_name: '' }]);


  function getUser(user) {
    fetch('https://api.github.com/users/' + user).
    then(response => response.json()).
    then(response => {
      if (response.message) return alert("User not found.");

      return response;
    }).
    then(response => {if (response) return setUser(response);});
  }

  function getRepos(user) {
    fetch('https://api.github.com/users/' + user + '/repos').
    then(response => response.json()).
    then(response => {
      if (response.message) return alert("Repositories not found.");
      return response;
    }).
    then(response => {if (response) return setRepos(response);});
  }

  return /*#__PURE__*/(
    React.createElement("div", null, /*#__PURE__*/
    React.createElement("div", { id: "search" }, /*#__PURE__*/
    React.createElement(SearchBar, null), /*#__PURE__*/
    React.createElement("button", { onClick: () => {
        const value = document.getElementById('search-bar').value || 'lorenzo-lipp';
        getUser(value);
        getRepos(value);
      } }, /*#__PURE__*/
    React.createElement("svg", { className: "icon", xmlns: "http://www.w3.org/2000/svg", viewBox: "0 0 460 512" }, /*#__PURE__*/
    React.createElement("path", { d: "M220.6 130.3l-67.2 28.2V43.2L98.7 233.5l54.7-24.2v130.3l67.2-209.3zm-83.2-96.7l-1.3 4.7-15.2 52.9C80.6 106.7 52 145.8 52 191.5c0 52.3 34.3 95.9 83.4 105.5v53.6C57.5 340.1 0 272.4 0 191.6c0-80.5 59.8-147.2 137.4-158zm311.4 447.2c-11.2 11.2-23.1 12.3-28.6 10.5-5.4-1.8-27.1-19.9-60.4-44.4-33.3-24.6-33.6-35.7-43-56.7-9.4-20.9-30.4-42.6-57.5-52.4l-9.7-14.7c-24.7 16.9-53 26.9-81.3 28.7l2.1-6.6 15.9-49.5c46.5-11.9 80.9-54 80.9-104.2 0-54.5-38.4-102.1-96-107.1V32.3C254.4 37.4 320 106.8 320 191.6c0 33.6-11.2 64.7-29 90.4l14.6 9.6c9.8 27.1 31.5 48 52.4 57.4s32.2 9.7 56.8 43c24.6 33.2 42.7 54.9 44.5 60.3s.7 17.3-10.5 28.5zm-9.9-17.9c0-4.4-3.6-8-8-8s-8 3.6-8 8 3.6 8 8 8 8-3.6 8-8z" })))), /*#__PURE__*/



    React.createElement("div", { className: "info" }, /*#__PURE__*/
    React.createElement("img", { src: user.avatar_url }), /*#__PURE__*/
    React.createElement("p", { className: "info-name" }, user.login), /*#__PURE__*/
    React.createElement("p", null, user.bio), /*#__PURE__*/
    React.createElement("p", null, user.public_repos, " repositories")), /*#__PURE__*/

    React.createElement(Repositories, { repos: repos })));


}

function SearchBar() {
  return /*#__PURE__*/(
    React.createElement("input", { type: "text", placeholder: "Search a github profile...", id: "search-bar" }));

}

function Repositories(props) {
  const { repos } = props;

  function iterateRepos(value, index) {
    return /*#__PURE__*/(
      React.createElement("div", { className: "repository", key: `Repo${index}` }, /*#__PURE__*/
      React.createElement("h2", null, /*#__PURE__*/React.createElement("a", { href: value.html_url, target: "_blank" }, value.name)), /*#__PURE__*/
      React.createElement("h4", null, value.description || "No description...")));


  }

  return /*#__PURE__*/(
    React.createElement("div", { className: "repositories" },
    repos.map(iterateRepos)));


}

document.addEventListener('DOMContentLoaded', function () {
  ReactDOM.render( /*#__PURE__*/React.createElement(App, null), document.getElementById('root'));
  document.getElementsByTagName('button')[0].click();
  document.getElementById('search-bar').onkeyup = e => {
    if (e.key === "Enter") {
      document.getElementsByTagName('button')[0].click();
    }
  };
});