// Kiểm tra trạng thái đăng nhập và cập nhật navigation
function initAuthUI() {
  const token = localStorage.getItem('token');
  const loginLinks = document.querySelectorAll('[data-auth="login"]');
  const logoutLinks = document.querySelectorAll('[data-auth="logout"]');

  if (token) {
    // Người dùng đã đăng nhập
    loginLinks.forEach(el => el.style.display = 'none');
    logoutLinks.forEach(el => el.style.display = 'inline');
  } else {
    // Người dùng chưa đăng nhập
    loginLinks.forEach(el => el.style.display = 'inline');
    logoutLinks.forEach(el => el.style.display = 'none');
  }
}

// Xử lý logout
function handleLogout(event) {
  event.preventDefault();
  localStorage.removeItem('token');
  localStorage.removeItem('user');
  window.location.href = '/';
}

// Thêm header authorization nếu có token
function getAuthHeader() {
  const token = localStorage.getItem('token');
  return token ? { 'Authorization': `Bearer ${token}` } : {};
}

// Khởi chạy khi DOM ready
document.addEventListener('DOMContentLoaded', function() {
  initAuthUI();
  
  // Thêm listener cho logout button
  const logoutBtn = document.getElementById('logoutBtn');
  if (logoutBtn) {
    logoutBtn.addEventListener('click', handleLogout);
  }
});
