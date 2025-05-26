<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta content="width=device-width, initial-scale=1.0" name="viewport">
	
	<title>Quản lý người dùng</title>
	<meta content="" name="description">
	<meta content="" name="keywords">
	
	<%@ include file="fragments/css_include.jsp" %>
	 
 	<script>
	    function toggleInput(checkboxId, inputId) {
	        const checkbox = document.getElementById(checkboxId);
	        const input = document.getElementById(inputId);
	        input.disabled = !checkbox.checked;
	    }
	</script>
</head>

<body>
	<%@ include file="fragments/header.jsp" %>
	<%@ include file="fragments/sidebar.jsp" %>
	
	<main id="main" class="main">
	
		<div class="pagetitle">
			<h1>Quản lý người dùng</h1>
			<nav>
				<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="index.html">Trang chủ</a></li>
				<li class="breadcrumb-item">Quản lý</li>
				<li class="breadcrumb-item active">Người dùng</li>
			</ol>
			</nav>
		</div>
		<section class="section">
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
        				<div class="card-body">
        				
        				<div class="d-flex align-items-center justify-content-start gap-2 my-3">
						    <h5 class="mb-0">Bảng các người dùng</h5>
						    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addModal">
						        Thêm
						    </button>
						    <span>Trang</span>
						    <select class="form-select w-auto" aria-label="Page selection" id="pageSelect">
						        <c:forEach var="page" begin="1" end="${totalPages}">
						            <option value="${page}" ${page == currentPage ? 'selected' : ''}>${page}</option>
						        </c:forEach>
						    </select>
						    <script>
							    document.addEventListener('DOMContentLoaded', () => {
							        const selectElement = document.getElementById('pageSelect');
							        const baseUrl = `${pageContext.request.contextPath}/user?page=`;
							        selectElement.addEventListener('change', (event) => {
							            const selectedPage = event.target.value;
							            if (selectedPage) {
							                window.location.href = baseUrl + selectedPage;
							            }
							        });
							    });
							</script>
							<p class="mb-0">/ ${totalPages}</p>		
							<p class="mb-0">Số người dùng: ${totalItems} </p>				    
						</div>
        				
        				
          				<c:if test="${flag == 'fail'}">
<%-- 					        <p style="z-index: 999999" class="error">${message}</p> --%>
					        <span  style="z-index: 999999" class="alert alert-danger bg-danger text-light border-0 alert-dismissible fade show position-fixed bottom-0 end-0 m-3" role="alert">
                				${message}
                				<button type="button" class="btn-close btn-close-white" data-bs-dismiss="alert" aria-label="Close"></button>
              				</span>
					    </c:if>
					    <c:if test="${flag == 'ok'}">
<%-- 					        <p class="success">${message}</p> --%>
					        <span  style="z-index: 999999" class="alert alert-success bg-success text-light border-0 alert-dismissible fade show position-fixed bottom-0 end-0 m-3" role="alert">
							    ${message}
							    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="alert" aria-label="Close"></button>
							</span>
					    </c:if>  
						<table class="table datatable">
  							<thead>
								<tr>
<!-- 									<th scope="col" class="align-middle">ID</th> -->
<!-- 									<th scope="col" class="align-middle">Tài khoản</th> -->
									<th scope="col">Họ tên</th>
									<th scope="col">Điện thoại</th>
									<th scope="col">Email</th>
									<th scope="col">Địa chỉ</th>
<!-- 									<th scope="col">Ngày sinh</th> -->
<!-- 									<th scope="col">Giới tính</th> -->
<!-- 									<th scope="col">Ghi chú</th> -->
									<th scope="col">Hành động</th>
								</tr>
							</thead>
  							<tbody>
  							<c:forEach var="row" items="${items}">
							    <tr title="Ngày sinh: ${row.userBirthday}&#013;Giới tính: ${row.userGender}&#013;Ghi chú: ${row.userNote}&#013;Ngày tạo: ${row.userCreatedAt}&#013;Cập nhật cuối: ${row.userUpdatedAt}">
<%-- 							        <td class="align-middle">${row.userId}</td> --%>
<%-- 							        <td class="align-middle">${row.userUsername}</td> --%>
							        <td class="align-middle">${row.userFullname}</td>
							        <td class="align-middle">${row.userPhone}</td>
							        <td class="align-middle">${row.userEmail}</td>
							        <td class="align-middle">${row.userAddress}</td>
<%-- 							        <td class="align-middle">${row.userBirthday}</td> --%>
<%-- 							        <td class="align-middle">${row.userGender}</td> --%>
<%-- 							        <td class="align-middle">${row.userNote}</td> --%>
							        <td class="align-middle">
							            <!-- Delete Form -->
							            <form action="${pageContext.request.contextPath}/user" method="post" style="display: inline;">
							                <input type="hidden" name="action" value="delete">
							                <input type="hidden" name="user_id" value="${row.userId}">
							                <button type="submit" class="btn btn-danger btn-action">
							                    Xóa
							                </button>
							            </form>
							
							            <!-- Edit Button (opens modal) -->
							            <button type="button" class="btn btn-warning btn-action" data-bs-toggle="modal" data-bs-target="#editModal${row.userId}">
							                Sửa
							            </button>
							
							            <!-- Modal for Editing User -->
							            <div class="modal fade" id="editModal${row.userId}" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
							                <div class="modal-dialog">
							                    <div class="modal-content">
							                        <div class="modal-header">
							                            <h5 class="modal-title" id="editPhongModalLabel">
							                                Sửa thông tin người dùng có ID: ${row.userId}
							                            </h5>
							                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							                        </div>
							                        <div class="modal-body">
							                            <form action="${pageContext.request.contextPath}/user" method="post" enctype="multipart/form-data">
							                                <input type="hidden" name="action" value="update">
							                                <input type="hidden" name="user_id" value="${row.userId}">
							
							                                <!-- Form fields for editing user info -->
							                                <div class="mb-3">
							                                    <label for="user_username" class="form-label">Tài khoản</label>
							                                    <input type="text" class="form-control" id="user_username" name="user_username" value="${row.userUsername}" required>
							                                </div>
							
							                                <div class="mb-3">
							                                    <span class="form-switch me-${row.userId}">
							                                        <input class="form-check-input" type="checkbox" id="is_update_user_password${row.userId}" name="is_update_user_password" onchange="toggleInput('is_update_user_password${row.userId}', 'user_password${row.userId}')">
							                                    </span>
							                                    <label for="user_password${row.userId}" class="form-label">Mật khẩu mới</label>
							                                    <input disabled type="password" class="form-control" id="user_password${row.userId}" name="user_password" minlength="8" required>
							                                </div>
							
							                                <!-- Other form fields for user details -->
							                                <div class="mb-3">
							                                    <label for="user_fullname" class="form-label">Tên đầy đủ</label>
							                                    <input type="text" class="form-control" id="user_fullname" name="user_fullname" value="${row.userFullname}" required>
							                                </div>
							
							                                <div class="mb-3">
							                                    <label for="user_phone" class="form-label">Điện thoại</label>
							                                    <input type="tel" class="form-control" id="user_phone" name="user_phone" value="${row.userPhone}" required>
							                                </div>
							
							                                <div class="mb-3">
							                                    <label for="user_email" class="form-label">Email</label>
							                                    <input type="email" class="form-control" id="user_email" name="user_email" value="${row.userEmail}" required>
							                                </div>
							
							                                <div class="mb-3">
							                                    <label for="user_address" class="form-label">Địa chỉ</label>
							                                    <input type="text" class="form-control" id="user_address" name="user_address" value="${row.userAddress}" required>
							                                </div>
							
							                                <div class="mb-3">
							                                    <label for="user_birthday" class="form-label">Ngày sinh</label>
							                                    <input type="date" class="form-control" id="user_birthday" name="user_birthday" value="${row.userBirthday}" required>
							                                </div>
							
							                                <div class="mb-3">
							                                    <label for="user_gender" class="form-label">Giới tính</label>
							                                    <input type="text" class="form-control" id="user_gender" name="user_gender" value="${row.userGender}" required>
							                                </div>
							
							                                <div class="mb-3">
							                                    <label for="user_note" class="form-label">Mô tả</label>
							                                    <textarea class="form-control" id="user_note" name="user_note" rows="3" required>${row.userNote}</textarea>
							                                </div>
							
							                                <button type="submit" class="btn btn-warning">Sửa</button>
							                            </form>
							                        </div>
							                    </div>
							                </div>
							            </div>
							        </td>
							    </tr>
							</c:forEach>

  							</tbody>
						</table>

        				</div>
      				</div>
    			</div>
  			</div>
		</section>
	</main><!-- End #main -->
	
	<div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addPhongModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addPhongModalLabel">Thêm người dùng</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="${pageContext.request.contextPath}/user" method="post" enctype="multipart/form-data">
						<input type="hidden" name="action" value="add">
						<input type="hidden" name="user_id" value="${row['user_id']}">

						<div class="mb-3">
							<label for="user_username" class="form-label">Tài khoản</label>
							<input type="text" class="form-control" id="user_username" name="user_username" required>
						</div>
						
						<div class="mb-3">
						    <label for="user_password" class="form-label">Mật khẩu</label>
						    <input type="password" class="form-control" id="user_password" name="user_password" required minlength="8">
						</div>
						
						<div class="mb-3">
							<label for="user_fullname" class="form-label">Tên đầy đủ</label>
							<input type="text" class="form-control" id="user_fullname" name="user_fullname" required>
						</div>

						<div class="mb-3">
							<label for="user_phone" class="form-label">Điện thoại</label>
							<input type="tel" class="form-control" id="user_phone" name="user_phone" required>
						</div>

						<div class="mb-3">
							<label for="user_email" class="form-label">Email</label>
							<input type="email" class="form-control" id="user_email" name="user_email" required>
						</div>
						
						<div class="mb-3">
							<label for="user_address" class="form-label">Địa chỉ</label>
							<input type="text" class="form-control" id="user_address" name="user_address" required>
						</div>
						
						<div class="mb-3">
							<label for="user_birthday" class="form-label">Ngày sinh</label>
							<input type="date" class="form-control" id="user_birthday" name="user_birthday" required>
						</div>
						
						<div class="mb-3">
							<label for="user_gender" class="form-label">Giới tính</label>
							<input type="text" class="form-control" id="user_gender" name="user_gender" required>
						</div>

						<!-- mô tả -->
						<div class="mb-3">
							<label for="user_note" class="form-label">Mô tả</label>
							<textarea class="form-control" id="user_note" name="user_note" rows="3" required>
								${row['user_note']}
							</textarea>
						</div>
						<button type="submit" class="btn btn-success">Thêm</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	 <!-- ======= Footer ======= -->
	<footer id="footer" class="footer">
	  <!-- <div class="copyright">
	    &copy; Copyright <strong><span>NiceAdmin</span></strong>. All Rights Reserved
	  </div>
	  <div class="credits">
	    Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
	  </div> -->
	</footer>
	<!-- End Footer -->
	
	<!-- Vendor JS Files -->
	<script src="assets/vendor/apexcharts/apexcharts.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="assets/vendor/chart.js/chart.umd.js"></script>
	<script src="assets/vendor/echarts/echarts.min.js"></script>
	<script src="assets/vendor/quill/quill.js"></script>
	<script src="assets/vendor/simple-datatables/simple-datatables.js"></script>
	<script src="assets/vendor/tinymce/tinymce.min.js"></script>
	<script src="assets/vendor/php-email-form/validate.js"></script>
	
	<!-- Template Main JS File -->
	<script src="assets/js/main.js"></script>

</body>

</html>