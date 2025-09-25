package com.green.demo.dto;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.green.demo.entity.UserEntity;

public class CustomerUserDetails implements UserDetails {

	private UserEntity userEntity;
	public CustomerUserDetails(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	// Role 반환
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		
		 collection.add(new GrantedAuthority() {		
			 @Override
			 public String getAuthority() {
				 return userEntity.getRole();
			 }
		});
		return collection;
	}

	// Password 반환
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return userEntity.getPassword();
	}

	// Username 반환
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userEntity.getUsername();
	}

	// 계정(Account), Expired: 유효기간 만료
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	// 계정 잠김(Lock)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// Credentail(비밀번호)	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정사용 가능여부
	@Override
	public boolean isEnabled() {
		return true;
	}

}