// Get token from cookie or localStorage
function getToken() {
  // Try to get from cookie first (more reliable for page navigation)
  const cookies = document.cookie.split(";");
  for (let cookie of cookies) {
    const [name, value] = cookie.trim().split("=");
    if (name === "token" && value) {
      return decodeURIComponent(value);
    }
  }
  // Fallback to localStorage
  return localStorage.getItem("token");
}

// Kiểm tra trạng thái đăng nhập và cập nhật navigation
function initAuthUI() {
  const token = getToken();
  const loginLinks = document.querySelectorAll('[data-auth="login"]');
  const logoutLinks = document.querySelectorAll('[data-auth="logout"]');

  if (token) {
    // Người dùng đã đăng nhập
    loginLinks.forEach((el) => (el.style.display = "none"));
    logoutLinks.forEach((el) => (el.style.display = "inline"));
  } else {
    // Người dùng chưa đăng nhập
    loginLinks.forEach((el) => (el.style.display = "inline"));
    logoutLinks.forEach((el) => (el.style.display = "none"));
  }
}

// Xử lý logout
function handleLogout(event) {
  event.preventDefault();

  // Clear localStorage
  localStorage.removeItem("token");
  localStorage.removeItem("user");

  // Clear cookie (set maxAge to 0)
  document.cookie = "token=; Max-Age=0; path=/;";

  window.location.href = "/login";
}

// Thêm header authorization nếu có token (cho API calls)
function getAuthHeader() {
  const token = getToken();
  return token ? { Authorization: `Bearer ${token}` } : {};
}

// Khởi chạy khi DOM ready
document.addEventListener("DOMContentLoaded", function () {
  initAuthUI();

  // Thêm listener cho logout button
  const logoutBtn = document.getElementById("logoutBtn");
  if (logoutBtn) {
    logoutBtn.addEventListener("click", handleLogout);
  }
});
