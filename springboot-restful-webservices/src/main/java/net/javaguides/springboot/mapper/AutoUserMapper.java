package net.javaguides.springboot.mapper;


import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoUserMapper {

AutoUserMapper MAPPER = Mappers.getMapper(AutoUserMapper.class);

UserDto maptoUserDto(User user);

User maptoUser(UserDto userDto);

}
